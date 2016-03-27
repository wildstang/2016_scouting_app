package org.wildstang.wildrank.androidv2.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.views.scouting.ScoutingSpinnerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a custom view that allows the user to manipulate game objects and robots (magnets) on a
 * field
 * This will have to be remade for every years game. Find a picture of the field with game objects on
 * it and crop out the objects. Then put the field and the cropped objects in drawable.
 */
public class WhiteboardView extends View {
    //all the images
    Bitmap field;
    Bitmap boulder;
    Bitmap portcullis;
    Bitmap moat;
    Bitmap ramparts;
    Bitmap drawBridge;
    Bitmap sallyPort;
    Bitmap roughTerrain;
    Bitmap rockWall;
    Bitmap quadRamp;
    Bitmap robot;
    Bitmap blank;

    //if the whiteboard has been initialized
    boolean run = false;
    //if the pen mode is enabled
    boolean penOn = false;
    boolean isPenRed;
    //if a magnet is held
    boolean magnetHeld = false;
    //if any defense location is toggled
    boolean defenseToggle;

    //scale of the magnets and field to the screen
    double fieldScale;
    //scale of the magnet sources on the left
    double magScale;
    //the current magnet held (ignored if magnetHeld is false
    int currentMagnet = 0;
    //offsets for holding the magnet (basically where the touchpoint is on the magnet)
    int xOffset;
    int yOffset;
    //used to create reletive positions for the drop down's for defenses
    int orginButtonX;
    int orginButtonY;
    int buttonX;
    int buttonY;
    String position;
    int defenseSizeWidth;
    int defenseSizeHeight;

    //the magnet sources on the left
    List<GamePiece> pieces = new ArrayList<>();
    //all the magnets on the screen
    List<Magnet> magnets = new ArrayList<>();
    //the buttons on the right
    List<Button> buttons = new ArrayList<>();
    //all the points for the pen
    List<List<Point>> points = new ArrayList<>();
    //dropdowns for defenses
    List<Button> defensesBtn = new ArrayList<>();
    //map defenses for drawing
    List<GamePiece> mapDefenses = new ArrayList<>();

    //this is a constructor, you better know what that is
    public WhiteboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //loads all the raw images
        field = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.field);
        boulder = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.boulder);
        portcullis = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.portcullis);
        moat = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.moat);
        ramparts = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ramparts);
        drawBridge = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.drawbridge);
        sallyPort = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sallyport);
        roughTerrain = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.roughterrain);
        rockWall = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rockwall);
        quadRamp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.quadramp);
        robot = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.dozer);
        blank = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.blank);
    }

    //this is run when the view is created
    //it initializes images and buttons
    public void init() {
        //setups field and scales
        fieldScale = ((5 * (double) getWidth()) / (6 * (double) field.getWidth()));

        System.out.println("width " + getWidth()); // 1280
        System.out.println("height " + getHeight()); // 663
        System.out.println("field height " + (int) (field.getHeight() * fieldScale));
        System.out.println("field width " + (int) (field.getWidth() * fieldScale));
        System.out.println("scale " + fieldScale);
        magScale = ((double) getHeight() / (double) field.getHeight());
        field = Bitmap.createScaledBitmap(field, (int) (field.getWidth() * fieldScale), (int) (field.getHeight() * fieldScale), false);
        //scale = (((double) getHeight() / 8.0) / (double) tote.getWidth());
        defenseSizeWidth = (int) (40 * fieldScale);
        defenseSizeHeight = (int) (43 * fieldScale);

        //creates the buttons
        pieces.add(new GamePiece(((getWidth() / 6) - (boulder.getWidth() * 3)) / 2, (2 * (getHeight() / 6)),
                Bitmap.createScaledBitmap(boulder, (int) boulder.getWidth() * 3, (int) boulder.getWidth() * 3, false),
                Bitmap.createScaledBitmap(boulder, (int) (boulder.getWidth() * 2.5), (int) (boulder.getHeight() * 2.5), false)));
        pieces.add(new GamePiece(0, (2 * (getHeight() / 6)) + (boulder.getWidth() * 3),
                Bitmap.createScaledBitmap(robot, (int) (getWidth() / 6), (int) (getHeight() / 6), false),
                Bitmap.createScaledBitmap(robot, (int) (robot.getWidth() / 20), (int) (robot.getHeight() / 20), false)));

        //creates the side buttons for clearing and using the pen
        buttons.add(new Button(0, 0, getWidth() / 6, getHeight() / 6, "Clear!", false));
        buttons.add(new Button(0, buttons.get(0).getHeight(), getWidth() / 6, getHeight() / 6, "Pen On/Off", true));

        int b2x = (int) ((193 * fieldScale) + (getWidth() / 6));
        int b2y = (int) ((195 * fieldScale));

        int b3x = (int) ((193 * fieldScale) + (getWidth() / 6));
        int b3y = (int) ((151 * fieldScale));

        int b4x = (int) ((193 * fieldScale) + (getWidth() / 6));
        int b4y = (int) ((107 * fieldScale));

        int b5x = (int) ((193 * fieldScale) + (getWidth() / 6));
        int b5y = (int) ((63 * fieldScale));

        int r2x = (int) ((373 * fieldScale) + (getWidth() / 6));
        int r2y = (int) ((63 * fieldScale));

        int r3x = (int) ((373 * fieldScale) + (getWidth() / 6));
        int r3y = (int) ((107 * fieldScale));

        int r4x = (int) ((373 * fieldScale) + (getWidth() / 6));
        int r4y = (int) ((151 * fieldScale));

        int r5x = (int) ((373 * fieldScale) + (getWidth() / 6));
        int r5y = (int) ((195 * fieldScale));

        buttons.add(new Button(getWidth() / 6, (5 * getHeight() / 6), (5 * getWidth() / 6) / 8, getHeight() / 12, "R2", true));
        buttons.add(new Button((getWidth() / 6) + ((5 * getWidth() / 6) / 8), (5 * getHeight() / 6), (5 * getWidth() / 6) / 8, getHeight() / 12, "R3", true));
        buttons.add(new Button((getWidth() / 6) + (2 * ((5 * getWidth() / 6) / 8)), (5 * getHeight() / 6), (5 * getWidth() / 6) / 8, getHeight() / 12, "R4", true));
        buttons.add(new Button((getWidth() / 6) + (3 * ((5 * getWidth() / 6) / 8)), (5 * getHeight() / 6), (5 * getWidth() / 6) / 8, getHeight() / 12, "R5", true));
        buttons.add(new Button(getWidth() / 6, (11 * getHeight()) / 12, (5 * getWidth() / 6) / 8, getHeight() / 12, "B2", true));
        buttons.add(new Button((getWidth() / 6) + ((5 * getWidth() / 6) / 8), (11 * getHeight()) / 12, (5 * getWidth() / 6) / 8, getHeight() / 12, "B3", true));
        buttons.add(new Button((getWidth() / 6) + (2 * ((5 * getWidth() / 6) / 8)), (11 * getHeight()) / 12, (5 * getWidth() / 6) / 8, getHeight() / 12, "B4", true));
        buttons.add(new Button((getWidth() / 6) + (3 * ((5 * getWidth() / 6) / 8)), (11 * getHeight()) / 12, (5 * getWidth() / 6) / 8, getHeight() / 12, "B5", true));

        //list for the bitmap gamepiece defenses
        //corrisponds to R2
        mapDefenses.add(new GamePiece(r2x, r2y,
                Bitmap.createScaledBitmap(blank, (int) (40 * fieldScale), (int) (43 * fieldScale), false), null));
        //corrisponds to R3
        mapDefenses.add(new GamePiece(r3x, r3y,
                Bitmap.createScaledBitmap(blank, (int) (40 * fieldScale), (int) (43 * fieldScale), false), null));
        //corrisponds to R4
        mapDefenses.add(new GamePiece(r4x, r4y,
                Bitmap.createScaledBitmap(blank, (int) (40 * fieldScale), (int) (43 * fieldScale), false), null));
        //corrisponds to R5
        mapDefenses.add(new GamePiece(r5x, r5y,
                Bitmap.createScaledBitmap(blank, (int) (40 * fieldScale), (int) (43 * fieldScale), false), null));
        //corrisponds to B2
        mapDefenses.add(new GamePiece(b2x, b2y,
                Bitmap.createScaledBitmap(blank, (int) (40 * fieldScale), (int) (43 * fieldScale), false), null));
        //corrisponds to B3
        mapDefenses.add(new GamePiece(b3x, b3y,
                Bitmap.createScaledBitmap(blank, (int) (40 * fieldScale), (int) (43 * fieldScale), false), null));
        //corrisponds to B4
        mapDefenses.add(new GamePiece(b4x, b4y,
                Bitmap.createScaledBitmap(blank, (int) (40 * fieldScale), (int) (43 * fieldScale), false), null));
        //corrisponds to B5
        mapDefenses.add(new GamePiece(b5x, b5y,
                Bitmap.createScaledBitmap(blank, (int) (40 * fieldScale), (int) (43 * fieldScale), false), null));

        //listener for touching the screen
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //draws when touched
                invalidate();

                //positions where touched
                int x = (int) event.getX();
                int y = (int) event.getY();

                //switch to check whether the screen was pressed on, dragged on, or let go
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //if pressed on
                        if (!penOn) {
                            for (int i = 0; i < pieces.size(); i++) {
                                pieces.get(i).checkPress(x, y);
                            }
                        }

                        //otherwise

                        //if the pen is on add a new point
                        if (penOn) {
                            points.add(new ArrayList<Point>());
                            points.get(points.size() - 1).add(new Point(x, y));
                        }

                        //check if any magnets are being picked up
                        magnetHeld = false;
                        for (int i = 0; i < magnets.size(); i++) {
                            Magnet magnet = magnets.get(i);
                            if (x >= magnet.x && x <= magnet.x + magnet.img.getWidth() && y >= magnet.y && y <= magnet.y + magnet.img.getHeight()) {
                                currentMagnet = i;
                                magnetHeld = true;
                                xOffset = x - magnet.x;
                                yOffset = y - magnet.y;
                            }
                        }

                        //check if any of the buttons are being pressed
                        for (int i = 0; i < buttons.size(); i++) {
                            Button button = buttons.get(i);
                            if (x >= button.x && x <= button.x + button.width && y >= button.y && y <= button.y + button.height) {
                                //if its a toggle button toggle the state
                                if (button.toggle) {
                                    buttons.get(i).pushed = !buttons.get(i).pushed;
                                    if (button.name.equals("Pen On/Off")) {
                                        penOn = buttons.get(i).pushed;
                                    }
                                    if (button.name.equalsIgnoreCase("R2") || button.name.equalsIgnoreCase("R3") || button.name.equalsIgnoreCase("R4") || button.name.equalsIgnoreCase("R5") || button.name.equalsIgnoreCase("B2") || button.name.equalsIgnoreCase("B3") || button.name.equalsIgnoreCase("B4") || button.name.equalsIgnoreCase("B5")) {
                                        if (buttons.get(i).pushed) {
                                            position = button.name;
                                        }
                                        for (int d = 0; d < buttons.size(); d++) {
                                            Button place = buttons.get(d);
                                            if (place.name != position && place.name != "Pen On/Off") {
                                                buttons.get(d).pushed = false;
                                            }
                                        }
                                        defenseToggle = buttons.get(i).pushed;
                                        System.out.println("de toggle" + defenseToggle);
                                    }
                                    if (defenseToggle) {
                                        //creates the "buttons" in the dropdown
                                        defensesBtn.add(new Button((getWidth() / 6) + (4 * ((5 * getWidth() / 6) / 8)), (5 * getHeight() / 6), (5 * getWidth() / 6) / 8, getHeight() / 12, "Portcullis", false));
                                        defensesBtn.add(new Button((getWidth() / 6) + (5 * ((5 * getWidth() / 6) / 8)), (5 * getHeight() / 6), (5 * getWidth() / 6) / 8, getHeight() / 12, "Moat", false));
                                        defensesBtn.add(new Button((getWidth() / 6) + (6 * ((5 * getWidth() / 6) / 8)), (5 * getHeight() / 6), (5 * getWidth() / 6) / 8, getHeight() / 12, "Draw Bridge", false));
                                        defensesBtn.add(new Button((getWidth() / 6) + (7 * ((5 * getWidth() / 6) / 8)), (5 * getHeight() / 6), (5 * getWidth() / 6) / 8, getHeight() / 12, "Rock Wall", false));
                                        defensesBtn.add(new Button((getWidth() / 6) + (4 * ((5 * getWidth() / 6) / 8)), (11 * getHeight()) / 12, (5 * getWidth() / 6) / 8, getHeight() / 12, "Quad Ramp", false));
                                        defensesBtn.add(new Button((getWidth() / 6) + (5 * ((5 * getWidth() / 6) / 8)), (11 * getHeight()) / 12, (5 * getWidth() / 6) / 8, getHeight() / 12, "Ramparts", false));
                                        defensesBtn.add(new Button((getWidth() / 6) + (6 * ((5 * getWidth() / 6) / 8)), (11 * getHeight()) / 12, (5 * getWidth() / 6) / 8, getHeight() / 12, "Sally Port", false));
                                        defensesBtn.add(new Button((getWidth() / 6) + (7 * ((5 * getWidth() / 6) / 8)), (11 * getHeight()) / 12, (5 * getWidth() / 6) / 8, getHeight() / 12, "Rough Terrain", false));
                                    } else {
                                        defensesBtn = new ArrayList<>();
                                        System.out.println("made it");
                                    }
                                } else {
                                    //otherwise just set it pushed
                                    buttons.get(i).pushed = true;
                                }
                            } else if (!button.toggle) {
                                //if you didn't press a button and its not a toggle depress it
                                buttons.get(i).pushed = false;
                            }
                        }
                        for(int t = 0; t < defensesBtn.size(); t++){
                            Button button = defensesBtn.get(t);
                            if (x >= button.x && x <= button.x + button.width && y >= button.y && y <= button.y + button.height) {
                                if(!button.toggle){
                                    defensesBtn.get(t).pushed = true;
                                }
                            }else if (!button.toggle){
                                defensesBtn.get(t).pushed = false;
                            }
                        }

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //if dragged on

                        //if you are holding a magnet move it
                        if (magnetHeld) {
                            magnets.get(currentMagnet).update(x - xOffset, y - yOffset);
                        }

                        //if you are using the pen draw
                        if (penOn && points.size() > 0) {
                            points.get(points.size() - 1).add(new Point(x, y));
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        //if let go of
                        if ((x < (getWidth() / 6)) && (y > ((5 * getHeight()) / 6))) {
                            isPenRed = !isPenRed;
                        }
                        //check if you are letting go of a button
                        for (int i = 0; i < buttons.size(); i++) {
                            Button button = buttons.get(i);
                            if (x >= button.x && x <= button.x + button.width && y >= button.y && y <= button.y + button.height && button.pushed && !button.toggle) {
                                //if its the clear button clear all magnets and drawing
                                if (button.name.equals("Clear!")) {
                                    magnets = new ArrayList<>();
                                    points = new ArrayList<>();
                                }
                            }

                            //if its not a toggle depress it
                            if (!button.toggle) {
                                buttons.get(i).pushed = false;
                            }
                        }
                        for (int i = 0; i < defensesBtn.size(); i++) {
                            Button button = defensesBtn.get(i);
                            if (x >= button.x && x <= button.x + button.width && y >= button.y && y <= button.y + button.height && button.pushed && !button.toggle) {
                                if (button.name.equalsIgnoreCase("Quad Ramp")) {
                                    int b = -1;
                                    switch (position) {
                                        case "R2":
                                            b = 0;
                                            break;
                                        case "R3":
                                            b = 1;
                                            break;
                                        case "R4":
                                            b = 2;
                                            break;
                                        case "R5":
                                            b = 3;
                                            break;
                                        case "B2":
                                            b = 4;
                                            break;
                                        case "B3":
                                            b = 5;
                                            break;
                                        case "B4":
                                            b = 6;
                                            break;
                                        case "B5":
                                            b = 7;
                                            break;
                                        default:
                                            for (i = 0; i < mapDefenses.size(); i++) {
                                                mapDefenses.get(i).changeImage(blank);
                                            }
                                    }
                                    if (b != -1) {
                                        mapDefenses.get(b).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                        for (int j = 0; j < buttons.size(); j++){
                                            if(buttons.get(j).name != "Pen On/Off"){
                                                buttons.get(j).pushed = false;
                                            }
                                        }
                                    }
                                } else if (button.name.equalsIgnoreCase("Portcullis")) {
                                    int b = -1;
                                    switch (position) {
                                        case "R2":
                                            b = 0;
                                            break;
                                        case "R3":
                                            b = 1;
                                            break;
                                        case "R4":
                                            b = 2;
                                            break;
                                        case "R5":
                                            b = 3;
                                            break;
                                        case "B2":
                                            b = 4;
                                            break;
                                        case "B3":
                                            b = 5;
                                            break;
                                        case "B4":
                                            b = 6;
                                            break;
                                        case "B5":
                                            b = 7;
                                            break;
                                        default:
                                            for (i = 0; i < mapDefenses.size(); i++) {
                                                mapDefenses.get(i).changeImage(portcullis);
                                            }
                                    }
                                    if (b != -1) {
                                        mapDefenses.get(b).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                        for (int j = 0; j < buttons.size(); j++){
                                            if(buttons.get(j).name != "Pen On/Off"){
                                                buttons.get(j).pushed = false;
                                            }
                                        }
                                    }
                                } else if (button.name.equalsIgnoreCase("Moat")) {
                                    int b = -1;
                                    switch (position) {
                                        case "R2":
                                            b = 0;
                                            break;
                                        case "R3":
                                            b = 1;
                                            break;
                                        case "R4":
                                            b = 2;
                                            break;
                                        case "R5":
                                            b = 3;
                                            break;
                                        case "B2":
                                            b = 4;
                                            break;
                                        case "B3":
                                            b = 5;
                                            break;
                                        case "B4":
                                            b = 6;
                                            break;
                                        case "B5":
                                            b = 7;
                                            break;
                                        default:
                                            for (i = 0; i < mapDefenses.size(); i++) {
                                                mapDefenses.get(i).changeImage(moat);
                                            }
                                    }
                                    if (b != -1) {
                                        mapDefenses.get(b).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                        for (int j = 0; j < buttons.size(); j++){
                                            if(buttons.get(j).name != "Pen On/Off"){
                                                buttons.get(j).pushed = false;
                                            }
                                        }
                                    }
                                } else if (button.name.equalsIgnoreCase("Ramparts")) {
                                    int b = -1;
                                    switch (position) {
                                        case "R2":
                                            b = 0;
                                            break;
                                        case "R3":
                                            b = 1;
                                            break;
                                        case "R4":
                                            b = 2;
                                            break;
                                        case "R5":
                                            b = 3;
                                            break;
                                        case "B2":
                                            b = 4;
                                            break;
                                        case "B3":
                                            b = 5;
                                            break;
                                        case "B4":
                                            b = 6;
                                            break;
                                        case "B5":
                                            b = 7;
                                            break;
                                        default:
                                            for (i = 0; i < mapDefenses.size(); i++) {
                                                mapDefenses.get(i).changeImage(ramparts);
                                            }
                                    }
                                    if (b != -1) {
                                        mapDefenses.get(b).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                        for (int j = 0; j < buttons.size(); j++){
                                            if(buttons.get(j).name != "Pen On/Off"){
                                                buttons.get(j).pushed = false;
                                            }
                                        }
                                    }
                                } else if (button.name.equalsIgnoreCase("Draw Bridge")) {
                                    int b = -1;
                                    switch (position) {
                                        case "R2":
                                            b = 0;
                                            break;
                                        case "R3":
                                            b = 1;
                                            break;
                                        case "R4":
                                            b = 2;
                                            break;
                                        case "R5":
                                            b = 3;
                                            break;
                                        case "B2":
                                            b = 4;
                                            break;
                                        case "B3":
                                            b = 5;
                                            break;
                                        case "B4":
                                            b = 6;
                                            break;
                                        case "B5":
                                            b = 7;
                                            break;
                                        default:
                                            for (i = 0; i < mapDefenses.size(); i++) {
                                                mapDefenses.get(i).changeImage(drawBridge);
                                            }
                                    }
                                    if (b != -1) {
                                        mapDefenses.get(b).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                        for (int j = 0; j < buttons.size(); j++){
                                            if(buttons.get(j).name != "Pen On/Off"){
                                                buttons.get(j).pushed = false;
                                            }
                                        }
                                    }
                                } else if (button.name.equalsIgnoreCase("Sally Port")) {
                                    int b = -1;
                                    switch (position) {
                                        case "R2":
                                            b = 0;
                                            break;
                                        case "R3":
                                            b = 1;
                                            break;
                                        case "R4":
                                            b = 2;
                                            break;
                                        case "R5":
                                            b = 3;
                                            break;
                                        case "B2":
                                            b = 4;
                                            break;
                                        case "B3":
                                            b = 5;
                                            break;
                                        case "B4":
                                            b = 6;
                                            break;
                                        case "B5":
                                            b = 7;
                                            break;
                                        default:
                                            for (i = 0; i < mapDefenses.size(); i++) {
                                                mapDefenses.get(i).changeImage(sallyPort);
                                            }
                                    }
                                    if (b != -1) {
                                        mapDefenses.get(b).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                        for (int j = 0; j < buttons.size(); j++){
                                            if(buttons.get(j).name != "Pen On/Off"){
                                                buttons.get(j).pushed = false;
                                            }
                                        }
                                    }
                                } else if (button.name.equalsIgnoreCase("Rough Terrain")) {
                                    int b = -1;
                                    switch (position) {
                                        case "R2":
                                            b = 0;
                                            break;
                                        case "R3":
                                            b = 1;
                                            break;
                                        case "R4":
                                            b = 2;
                                            break;
                                        case "R5":
                                            b = 3;
                                            break;
                                        case "B2":
                                            b = 4;
                                            break;
                                        case "B3":
                                            b = 5;
                                            break;
                                        case "B4":
                                            b = 6;
                                            break;
                                        case "B5":
                                            b = 7;
                                            break;
                                        default:
                                            for (i = 0; i < mapDefenses.size(); i++) {
                                                mapDefenses.get(i).changeImage(roughTerrain);
                                            }
                                    }
                                    if (b != -1) {
                                        mapDefenses.get(b).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                        for (int j = 0; j < buttons.size(); j++){
                                            if(buttons.get(j).name != "Pen On/Off"){
                                                buttons.get(j).pushed = false;
                                            }
                                        }
                                    }
                                } else if (button.name.equalsIgnoreCase("Rock Wall")) {
                                    int b = -1;
                                    switch (position) {
                                        case "R2":
                                            b = 0;
                                            break;
                                        case "R3":
                                            b = 1;
                                            break;
                                        case "R4":
                                            b = 2;
                                            break;
                                        case "R5":
                                            b = 3;
                                            break;
                                        case "B2":
                                            b = 4;
                                            break;
                                        case "B3":
                                            b = 5;
                                            break;
                                        case "B4":
                                            b = 6;
                                            break;
                                        case "B5":
                                            b = 7;
                                            break;
                                        default:
                                            for (i = 0; i < mapDefenses.size(); i++) {
                                                mapDefenses.get(i).changeImage(rockWall);
                                            }
                                    }
                                    if (b != -1) {
                                        mapDefenses.get(b).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                        for (int j = 0; j < buttons.size(); j++){
                                            if(buttons.get(j).name != "Pen On/Off"){
                                                buttons.get(j).pushed = false;
                                            }
                                        }
                                    }
                                }

                            }else if(!button.toggle){
                                button.toggle = false;
                            }
                        }
                        //check if you are letting go of a magnet on the left column
                        for (int i = 0; i < magnets.size(); i++) {
                            if ((magnets.get(i).x < (getWidth() / 6)) && (magnets.get(i).y > ((5 * getHeight()) / 6))) {
                                //if it is left remove it
                                magnets.remove(i);
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }

    //this is where everything is drawn
    //it is currently set to be run every time the screen is touched
    @Override
    public void onDraw(Canvas canvas) {
        //if the fragment hasn't been initiated initiate it
        if (!run) {
            init();
            run = true;
        }
        Paint paint = new Paint();

        //draws a blank canvas
//        paint.setColor(Color.WHITE);
//        canvas.drawRect(0, (5 * getHeight()) / 6, getWidth() / 6, 0, paint);

        if (isPenRed) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.BLUE);
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, ((5 * getHeight()) / 6), ((5 * getHeight()) / 6), 0, paint);
        //draws the field and all the image buttons
        canvas.drawBitmap(field, getWidth() / 6, 0, null);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).draw(canvas);
        }

        //draws the defenses on the field
        for (int i = 0; i < mapDefenses.size(); i++) {
            mapDefenses.get(i).draw(canvas);
        }

        //draws all the buttons
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(canvas);
        }

        //draws all the magnets
        for (int i = 0; i < magnets.size(); i++) {
            magnets.get(i).draw(canvas);
        }

        //draws the dropdowns
        for (int i = 0; i < defensesBtn.size(); i++) {
            defensesBtn.get(i).draw(canvas);
        }

        //draws the pretty drawing you made
        for (int i = 0; i < points.size(); i++) {
            for (int j = 1; j < points.get(i).size(); j++) {
                if (j > 0) {
                    Point last = points.get(i).get(j - 1);
                    Point point = points.get(i).get(j);
                    canvas.drawLine(last.x, last.y, point.x, point.y, paint);
                }
            }
        }
    }

    //this is a object for the magnets that can be moved around
    public class Magnet {
        int x, y;
        Bitmap img;

        public Magnet(int x, int y, Bitmap img) {
            this.x = x;
            this.y = y;
            this.img = img;
        }

        //updates the position of the magnet
        public void update(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //draws the image on the canvas at its position
        public void draw(Canvas c) {
            c.drawBitmap(img, x, y, null);
        }
    }

    //this is an object for the custom buttons on the right
    public class Button {
        int x, y, width, height;
        String name;
        boolean pushed;
        boolean toggle;

        public Button(int x, int y, int width, int height, String name, boolean toggle) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.name = name;
            this.toggle = toggle;
            pushed = false;
        }

        //draws my custom buttons
        public void draw(Canvas c) {
            Paint paint = new Paint();
            //if its pressed it's dark
            if (pushed) {
                paint.setColor(Color.DKGRAY);
            } else {
                //otherwise it's light
                paint.setColor(Color.LTGRAY);
            }
            c.drawRect(x, y, x + width, y + height, paint);
            paint.setColor(Color.BLACK);
            c.drawText(name, x + width / 3, y + height / 3, paint);
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }

    //this is a object for easily containing an x and y coordinate
    //it's specifically used for drawing
    public class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //this is the source of magnets on the left side of the screen
    public class GamePiece {
        int x, y;
        Bitmap image;
        Bitmap magnetImage;

        //needs the x and y position the scaled up image and the magnet image
        public GamePiece(int x, int y, Bitmap image, Bitmap magnetImage) {
            this.x = x;
            this.y = y;
            this.image = image;
            this.magnetImage = magnetImage;
        }

        //used to check if it is pressed
        public void checkPress(int tX, int tY) {
            if (tX > x && tX < x + image.getWidth() && tY > y && tY < y + image.getHeight()) {
                currentMagnet = magnets.size();
                magnets.add(new Magnet(x, y, magnetImage));
                magnetHeld = true;
            }
        }

        //draws the source
        public void draw(Canvas c) {
            c.drawBitmap(image, x, y, null);
        }

        public int getHeight() {
            return image.getHeight();
        }

        public int getWidth() {
            return image.getWidth();
        }

        public int getMagnetHeight() {
            return magnetImage.getHeight();
        }

        public int getMagnetWidth() {
            return magnetImage.getWidth();
        }

        public void changeImage(Bitmap newImage) {
            this.image = newImage;
        }
    }
}