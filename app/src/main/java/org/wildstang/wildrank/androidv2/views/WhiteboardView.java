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
    Bitmap lowBar;
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
    //if a magnet is held
    boolean magnetHeld = false;

    //scale of the magnets and field to the screen
    double scale;
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
        lowBar = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.lowbar);
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
        magScale = ((double) getHeight() / (double) field.getHeight());
        field = Bitmap.createScaledBitmap(field, (int) (magScale * field.getWidth()), getHeight(), false);
        //scale = (((double) getHeight() / 8.0) / (double) tote.getWidth());

        //creates the buttons
        
        pieces.add(new GamePiece(10, 10, Bitmap.createScaledBitmap(boulder, (int) (boulder.getWidth()), (int) (boulder.getHeight()), false), Bitmap.createScaledBitmap(boulder, (int) (boulder.getWidth()), (int) (boulder.getHeight()), false)));
        pieces.add(new GamePiece(10, 10, Bitmap.createScaledBitmap(robot, pieces.get(2).getWidth(), (int) (robot.getHeight() * ((double) pieces.get(2).getWidth() / (double) robot.getWidth())), false), Bitmap.createScaledBitmap(robot, 2 * pieces.get(2).getMagnetWidth(), 2 * (int) (robot.getHeight() * ((double) pieces.get(2).getMagnetWidth() / (double) robot.getWidth())), false)));

        //creates the side buttons for clearing and using the pen
        int width = ((getWidth() / 6) + field.getWidth());
        int buttonWidth = getWidth() - (width + 20);
        buttons.add(new Button(width + 10, 10, buttonWidth, 2 * buttonWidth / 3 + 10, "Clear!", false));
        buttons.add(new Button(width + 10, 2 * buttonWidth / 3 + 30, buttonWidth, 2 * buttonWidth / 3 + 10, "Pen On/Off", true));
        buttons.add(new Button(width + 10, 3 * buttonWidth / 3 + 30, buttonWidth, 2 * buttonWidth / 3 + 10, "Clear Menus", false));

        buttons.add(new Button(10, 10, 40, 43, "R1", false));
        buttons.add(new Button(10, 10, 40, 43, "R2", false));
        buttons.add(new Button(10, 10, 40, 43, "R3", false));
        buttons.add(new Button(10, 10, 40, 43, "R4", false));
        buttons.add(new Button(10, 10, 40, 43, "B1", false));
        buttons.add(new Button(10, 10, 40, 43, "B2", false));
        buttons.add(new Button(10, 10, 40, 43, "B3", false));
        buttons.add(new Button(10, 10, 40, 43, "B4", false));

        //list for the bitmap gamepiece defenses
        //corrisponds to R1
        mapDefenses.add(new GamePiece(10,10,Bitmap.createBitmap(blank),Bitmap.createBitmap(blank)));
        //corrisponds to R2
        mapDefenses.add(new GamePiece(10,10,Bitmap.createBitmap(blank),Bitmap.createBitmap(blank)));
        //corrisponds to R3
        mapDefenses.add(new GamePiece(10,10,Bitmap.createBitmap(blank),Bitmap.createBitmap(blank)));
        //corrisponds to R4
        mapDefenses.add(new GamePiece(10,10,Bitmap.createBitmap(blank),Bitmap.createBitmap(blank)));
        //corrisponds to B1
        mapDefenses.add(new GamePiece(10,10,Bitmap.createBitmap(blank),Bitmap.createBitmap(blank)));
        //corrisponds to B2
        mapDefenses.add(new GamePiece(10,10,Bitmap.createBitmap(blank),Bitmap.createBitmap(blank)));
        //corrisponds to B3
        mapDefenses.add(new GamePiece(10,10,Bitmap.createBitmap(blank),Bitmap.createBitmap(blank)));
        //corrisponds to B4
        mapDefenses.add(new GamePiece(10,10,Bitmap.createBitmap(blank),Bitmap.createBitmap(blank)));

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

                        //check if either of the buttons are being pressed
                        for (int i = 0; i < buttons.size(); i++) {
                            Button button = buttons.get(i);
                            if (x >= button.x && x <= button.x + button.width && y >= button.y && y <= button.y + button.height) {
                                //if its a toggle button toggle the state
                                if (button.toggle) {
                                    buttons.get(i).pushed = !buttons.get(i).pushed;
                                    if (button.name.equals("Pen On/Off")) {
                                        penOn = buttons.get(i).pushed;
                                    }
                                } else {
                                    //otherwise just set it pushed
                                    buttons.get(i).pushed = true;
                                }
                            } else if (!button.toggle) {
                                //if you didn't press a button and its not a toggle depress it
                                buttons.get(i).pushed = false;
                            }
                            if (button.name.equals("R1") || button.name.equals("R2") || button.name.equals("R3") || button.name.equals("R4") || button.name.equals("B1") || button.name.equals("B2") || button.name.equals("B3") || button.name.equals("B4")) {
                                orginButtonX = button.x;
                                orginButtonY = button.y;
                                String position = button.name;

                                //creates the "buttons" in the dropdown
                                defensesBtn.add(new Button(orginButtonX + 40, orginButtonY, buttonX, buttonY, "Portcullis", false));
                                defensesBtn.add(new Button(orginButtonX + 40, orginButtonY - (1 * buttonY), buttonX, buttonY, "Moat", false));
                                defensesBtn.add(new Button(orginButtonX + 40, orginButtonY - (2 * buttonY), buttonX, buttonY, "Ramparts", false));
                                defensesBtn.add(new Button(orginButtonX + 40, orginButtonY - (3 * buttonY), buttonX, buttonY, "Draw Bridge", false));
                                defensesBtn.add(new Button(orginButtonX + 40, orginButtonY - (4 * buttonY), buttonX, buttonY, "Sally Port", false));
                                defensesBtn.add(new Button(orginButtonX + 40, orginButtonY - (5 * buttonY), buttonX, buttonY, "Rough Terrain", false));
                                defensesBtn.add(new Button(orginButtonX + 40, orginButtonY - (6 * buttonY), buttonX, buttonY, "Rock Wall", false));
                                defensesBtn.add(new Button(orginButtonX + 40, orginButtonY - (7 * buttonY), buttonX, buttonY, "Quad Ramp", false));
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
                            if (button.name.equals("Clear Menus")) {
                                defensesBtn = new ArrayList<>();
                            }
                        }
                        for (int i = 0; i < defensesBtn.size(); i++){
                            Button button = defensesBtn.get(i);
                            if(button.name.equals("Quad Ramp")){
                                switch (position){
                                    case "R1": mapDefenses.get(0).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                    case "R2": mapDefenses.get(1).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                    case "R3": mapDefenses.get(2).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                    case "R4": mapDefenses.get(3).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                    case "B1": mapDefenses.get(4).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                    case "B2": mapDefenses.get(5).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                    case "B3": mapDefenses.get(6).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                    case "B4": mapDefenses.get(7).changeImage(quadRamp);
                                        defensesBtn = new ArrayList<>();
                                    default: for(i = 0; i < mapDefenses.size(); i++){
                                        mapDefenses.get(i).changeImage(blank);
                                    }
                                        defensesBtn = new ArrayList<>();
                                }
                            }else if(button.name.equals("Portcullis")){
                                switch (position){
                                    case "R1": mapDefenses.get(0).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                    case "R2": mapDefenses.get(1).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                    case "R3": mapDefenses.get(2).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                    case "R4": mapDefenses.get(3).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                    case "B1": mapDefenses.get(4).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                    case "B2": mapDefenses.get(5).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                    case "B3": mapDefenses.get(6).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                    case "B4": mapDefenses.get(7).changeImage(portcullis);
                                        defensesBtn = new ArrayList<>();
                                    default: for(i = 0; i < mapDefenses.size(); i++){
                                        mapDefenses.get(i).changeImage(blank);
                                    }
                                        defensesBtn = new ArrayList<>();
                                }
                            }else if(button.name.equals("Moat")){
                                switch (position){
                                    case "R1": mapDefenses.get(0).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                    case "R2": mapDefenses.get(1).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                    case "R3": mapDefenses.get(2).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                    case "R4": mapDefenses.get(3).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                    case "B1": mapDefenses.get(4).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                    case "B2": mapDefenses.get(5).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                    case "B3": mapDefenses.get(6).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                    case "B4": mapDefenses.get(7).changeImage(moat);
                                        defensesBtn = new ArrayList<>();
                                    default: for(i = 0; i < mapDefenses.size(); i++){
                                        mapDefenses.get(i).changeImage(blank);
                                    }
                                        defensesBtn = new ArrayList<>();
                                }
                            }else if(button.name.equals("Ramparts")){
                                switch (position){
                                    case "R1": mapDefenses.get(0).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                    case "R2": mapDefenses.get(1).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                    case "R3": mapDefenses.get(2).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                    case "R4": mapDefenses.get(3).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                    case "B1": mapDefenses.get(4).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                    case "B2": mapDefenses.get(5).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                    case "B3": mapDefenses.get(6).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                    case "B4": mapDefenses.get(7).changeImage(ramparts);
                                        defensesBtn = new ArrayList<>();
                                    default: for(i = 0; i < mapDefenses.size(); i++){
                                        mapDefenses.get(i).changeImage(blank);
                                    }
                                        defensesBtn = new ArrayList<>();
                                }
                            }else if(button.name.equals("Draw Bridge")){
                                switch (position){
                                    case "R1": mapDefenses.get(0).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                    case "R2": mapDefenses.get(1).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                    case "R3": mapDefenses.get(2).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                    case "R4": mapDefenses.get(3).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                    case "B1": mapDefenses.get(4).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                    case "B2": mapDefenses.get(5).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                    case "B3": mapDefenses.get(6).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                    case "B4": mapDefenses.get(7).changeImage(drawBridge);
                                        defensesBtn = new ArrayList<>();
                                    default: for(i = 0; i < mapDefenses.size(); i++){
                                        mapDefenses.get(i).changeImage(blank);
                                    }
                                        defensesBtn = new ArrayList<>();
                                }
                            }else if(button.name.equals("Sally Port")){
                                switch (position){
                                    case "R1": mapDefenses.get(0).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                    case "R2": mapDefenses.get(1).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                    case "R3": mapDefenses.get(2).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                    case "R4": mapDefenses.get(3).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                    case "B1": mapDefenses.get(4).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                    case "B2": mapDefenses.get(5).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                    case "B3": mapDefenses.get(6).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                    case "B4": mapDefenses.get(7).changeImage(sallyPort);
                                        defensesBtn = new ArrayList<>();
                                    default: for(i = 0; i < mapDefenses.size(); i++){
                                        mapDefenses.get(i).changeImage(blank);
                                    }
                                        defensesBtn = new ArrayList<>();
                                }
                            }else if(button.name.equals("Rough Terrain")){
                                switch (position){
                                    case "R1": mapDefenses.get(0).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                    case "R2": mapDefenses.get(1).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                    case "R3": mapDefenses.get(2).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                    case "R4": mapDefenses.get(3).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                    case "B1": mapDefenses.get(4).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                    case "B2": mapDefenses.get(5).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                    case "B3": mapDefenses.get(6).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                    case "B4": mapDefenses.get(7).changeImage(roughTerrain);
                                        defensesBtn = new ArrayList<>();
                                    default: for(i = 0; i < mapDefenses.size(); i++){
                                        mapDefenses.get(i).changeImage(blank);
                                    }
                                        defensesBtn = new ArrayList<>();
                                }
                            }else if(button.name.equals("Rock Wall")){
                                switch (position){
                                    case "R1": mapDefenses.get(0).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                    case "R2": mapDefenses.get(1).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                    case "R3": mapDefenses.get(2).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                    case "R4": mapDefenses.get(3).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                    case "B1": mapDefenses.get(4).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                    case "B2": mapDefenses.get(5).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                    case "B3": mapDefenses.get(6).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                    case "B4": mapDefenses.get(7).changeImage(rockWall);
                                        defensesBtn = new ArrayList<>();
                                    default: for(i = 0; i < mapDefenses.size(); i++){
                                        mapDefenses.get(i).changeImage(blank);
                                    }
                                        defensesBtn = new ArrayList<>();
                                }
                            }
                                                    }
                        //check if you are letting go of a magnet on the left column
                        for (int i = 0; i < magnets.size(); i++) {
                            if (magnets.get(i).x < getWidth() / 6) {
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
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth() / 6, getHeight(), paint);

        //draws the field and all the image buttons
        canvas.drawBitmap(field, getWidth() / 6, 0, null);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).draw(canvas);
        }

        //draws the defenses on the field
        for (int i = 0; i < mapDefenses.size(); i++){
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
        for (int i = 0; i < defensesBtn.size(); i++){
            defensesBtn.get(i).draw(canvas);
        }

        //draws the pretty drawing you made
        paint.setColor(Color.BLUE);
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

        public int getMagnetWidth() {return magnetImage.getWidth();}

        public void changeImage(Bitmap newImage){
            this.image = newImage;
        }
    }
}