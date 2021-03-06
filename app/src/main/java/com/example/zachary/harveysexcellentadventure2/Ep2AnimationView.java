package com.example.zachary.harveysexcellentadventure2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;


public class Ep2AnimationView extends View {
    Ep2MainActivity context;

    int width;
    int height;
    private float xLoc = 0;
    private float yLoc = 0;
    private boolean first_time;

    final int FRAME_RATE = 30;
    final int TIME_PER_FRAME = 1000/FRAME_RATE;
    private float acceleration;
    private long lastDrawTime;
    private long total_elapsed;
    private float yVelocity;
    private float xVelocity;
    private int direction_factor;
    final int MAX_HEIGHT = 1000; // the height of my model world (not same as screen)
    final int MIN_HEIGHT = 0;
    final int MAX_WIDTH = 1000;
    final int MIN_WIDTH = 0;

    private double counter = 0;
    private float yPosition; //position in my model world
    private float xPosition;
    private float yLocation;
    private float height_scale; //how to convert from model to screen
    private float width_scale;
    private int circleRadius;
    private Bitmap bm,b;
    //private Bitmap[] skins;
    private Integer score;
    private ArrayList<Chalk> box, toBeRemoved;
    private Random rand;
    private Boolean recentlyIncreased, gameOver=false;

    float[] reset = {-100, -100};
    float xClick = -100;
    float yClick = -100;
    float xChange = -10;
    float yChange = -10;


    public Ep2AnimationView(Context context){
        super(context);

    }
    public Ep2AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Ep2AnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        xLoc = 0;
        yLoc = 0;
        first_time = true;

        acceleration = .15F;
        lastDrawTime = 0;
        total_elapsed = 0;
        yVelocity = 0.800F;
        xVelocity = 0.800F;
        direction_factor = 1;

        this.context = (Ep2MainActivity) context;
    }

    private void initialize(){
        lastDrawTime = System.currentTimeMillis();
        first_time = false;
        float scale = width < height ? width : height;
        circleRadius = (int) scale/10;
        height_scale = ((float)height - circleRadius) / MAX_HEIGHT;
        //width_scale = ((float)width - circleRadius) / MAX_WIDTH;
        width_scale = (float) width / MAX_WIDTH;
        xLoc = width/2;
        yPosition = (int)(.85 * MAX_HEIGHT);
        xPosition = (int)(.5 * MAX_WIDTH);

        score = 0;

        /**
         skins = new Bitmap[6];
         skins[0] = BitmapFactory.decodeResource(getResources(), R.drawable.line1);
         skins[1] = BitmapFactory.decodeResource(getResources(), R.drawable.line2);
         skins[2] = BitmapFactory.decodeResource(getResources(), R.drawable.line3);
         skins[3] = BitmapFactory.decodeResource(getResources(), R.drawable.line4);
         skins[4] = BitmapFactory.decodeResource(getResources(), R.drawable.line5);
         skins[5] = BitmapFactory.decodeResource(getResources(), R.drawable.line6);
         recentlyIncreased = false;
         **/

        box = new ArrayList<>();
        toBeRemoved = new ArrayList<>();
        rand = new Random();
        box.add(new Chalk(BitmapFactory.decodeResource(getResources(), R.drawable.chalk), rand.nextInt(850) + 75, rand.nextInt(875) + 25, xVelocity + (rand.nextInt(20) - 10)/1000, yVelocity + (rand.nextInt(20) - 10)/1000, 1, 1));

    }

    @Override
    /**
     *  You may only use the variables given here. Do not make new variables or methods.
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!gameOver) {
            if (first_time)
                initialize();

            long elapsed = System.currentTimeMillis() - lastDrawTime;

            if (elapsed > TIME_PER_FRAME) {
                counter += 1;
                //System.out.println("xPOS: " + xPosition + " yPOS: " + yPosition + " Score: " + score + " Vel: " + yVelocity);
                lastDrawTime = System.currentTimeMillis();

                //Chalk newChalk = new Chalk(BitmapFactory.decodeResource(getResources(), R.drawable.chalk), rand.nextInt(1000), rand.nextInt(1000), xVelocity, yVelocity);

                if (box.size() == 0 && counter % 30 == 0)
                    box.add(new Chalk(BitmapFactory.decodeResource(getResources(), R.drawable.chalk), rand.nextInt(850) + 75, rand.nextInt(875) + 25, xVelocity + (rand.nextInt(20) - 10)/1000, yVelocity + (rand.nextInt(20) - 10)/1000));

                for (Chalk c : box){

                    c.xLocation += xVelocity * elapsed;
                    c.yLocation += yVelocity * elapsed;

                    if (c.xLocation > 925 && c.xDirection == 1){
                        xVelocity = xVelocity * -1;
                        c.xDirection = -1;
                        c.bounce += 1;
                    }

                    else if (c.xLocation < 75 && c.xDirection == -1){
                        xVelocity = xVelocity * -1;
                        c.xDirection = 1;
                        c.bounce += 1;
                    }

                    if (c.yLocation > 900 && c.yDirection == 1){
                        yVelocity = yVelocity * -1;
                        c.yDirection = -1;
                        c.bounce += 1;
                    }

                    else if(c.yLocation < 25 && c.yDirection == -1){
                        yVelocity = yVelocity * -1;
                        c.yDirection = 1;
                        c.bounce += 1;
                    }
                }




                /**

                 //makes every crowd member drop at velocity yVelocity
                 for (CrowdMember c : crowd) {
                 c.yLocation += yVelocity * elapsed;
                 }
                 //spawns new crowd members based on player location
                 if (xPosition == 500 && counter % 30 == 0) //gives first spawn a bit of buffer time
                 crowd.add(new CrowdMember(skins[rand.nextInt(skins.length)], 500));

                 if (xPosition == 250 && counter % 20 == 0)
                 crowd.add(new CrowdMember(skins[rand.nextInt(skins.length)], 250));

                 if (xPosition == 750 && counter % 20 == 0)
                 crowd.add(new CrowdMember(skins[rand.nextInt(skins.length)], 750));

                 //increase difficulty as score increases
                 if (score % 10 == 0 && !recentlyIncreased) {
                 yVelocity += .05;
                 //prevents difficulty from increasing more than once per each score milestone
                 recentlyIncreased = true;
                 }
                 **/

            }
            yLoc = height_scale * yPosition;
            xLoc = width_scale * xPosition;
            //drawHarvey(canvas);

            //System.out.println(context.getClickLoc()[0] + ", " + context.getClickLoc()[1]);
            xClick = context.getClickLoc()[0] / (float) 1.326;
            yClick = context.getClickLoc()[1] / 2;
            for (Chalk c : box) {
                drawChalk(canvas, c);

                if (xChange != xClick || yChange != yClick){
                    System.out.println("X: " + xClick + "   Y: " + yClick);
                    if (xClick < c.xLocation + 70 && xClick > c.xLocation - 70 && yClick < c.yLocation + 100 && yClick > c.yLocation - 100) {
                        System.out.print("-----------SUCCESS------------");
                        score += 1;
                        xVelocity += 0.100F;
                        yVelocity += 0.100F;
                        toBeRemoved.add(c);



                    }

                    xChange = xClick;
                    yChange = yClick;
                }


                if (c.bounce > 15) {
                    toBeRemoved.add(c);
                    gameOver = true;
                    context.gotoGameOver(score);
                }


                //collision detection
                /**
                 if (c.xLocation == xPosition && c.yLocation > (yPosition - (b.getHeight() / 2)) && c.yLocation < 950) {
                 gameOver = true;
                 yVelocity = 0;
                 System.out.println("---------- COLLISION!!! -------------");
                 context.gotoGameOver(score);
                 }**/
            }

            drawScore(canvas);

            for (Chalk c : toBeRemoved) {
                //score += 1;
                recentlyIncreased = false;
                box.remove(c);
            }
            toBeRemoved.clear();
            //drawCircle(canvas);
            this.invalidate();


        }
    }

    /**
     //@Override
     public void onClick(View v){
     System.out.println("CLICK");

     }

     /**
     private void drawHarvey(Canvas canvas){
     bm = BitmapFactory.decodeResource(getResources(), R.drawable.harvey);
     b = Bitmap.createScaledBitmap(bm, bm.getWidth() * 3, bm.getHeight() * 3, true);
     Paint harveyPaint = new Paint();
     canvas.drawBitmap(b, xLoc - (b.getWidth() / 2), yLoc, harveyPaint);
     }
     **/
    private void drawChalk(Canvas canvas, Chalk member) {
        Paint crowdPaint = new Paint();
        canvas.drawBitmap(member.bitmap, member.xLocation*width_scale - (member.bitmap.getWidth()/2), member.yLocation*height_scale, crowdPaint);
    }

    private void drawCircle(Canvas canvas) {
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.CYAN);
        circlePaint.setStrokeWidth(4);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setAntiAlias(true);

        canvas.drawCircle(xLoc, (int) (height_scale * .85 * MAX_HEIGHT), circleRadius, circlePaint);
    }
    private void drawScore(Canvas canvas) {
        Paint scorePaint = new Paint();
        scorePaint.setTextSize(200);

        canvas.drawText(score.toString(), 50, 200, scorePaint);
    }
    /**
     * This method is called by the Android platform when the app window size changes.
     * We store the initial setting of these so that we can compute the exact locations
     * to draw the components of our View.
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
    }
    public float getxVelocity(){ return xVelocity; }
    public void setxVelocity(float f){ xVelocity = f; }
    public float getxPosition(){ return xPosition; }
    public void setxPosition(float f) { xPosition = f; }
    public Integer getScore() { return score; }
    public long getClock() { return System.currentTimeMillis(); }
    public Boolean getGameOver(){ return gameOver; }
}
