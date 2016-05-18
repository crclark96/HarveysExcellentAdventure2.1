package com.example.zachary.harveysexcellentadventure2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class Ep3AnimationView extends View{
    Ep3MainActivity context;

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
    private ArrayList<Bitmap> skins;
    private Integer score;
    private ArrayList<CrowdMember> crowd, toBeRemoved;
    private Random rand;
    private Boolean recentlyIncreased, gameOver=false;
    private Boolean[] spawnConditions = {false, false, false, false, false};
    private CrowdMember harvey;
    private int nextSkin, interval;


    public Ep3AnimationView(Context context){
        super(context);

    }
    public Ep3AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Ep3AnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        xLoc = 0;
        yLoc = 0;
        first_time = true;


        acceleration = .0030F;
        lastDrawTime = 0;
        total_elapsed = 0;
        yVelocity = 0.000F;
        xVelocity = 0.750F;
        direction_factor = 1;

        this.context = (Ep3MainActivity) context;
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
        skins = new ArrayList<>();
        skins.add(BitmapFactory.decodeResource(getResources(), R.drawable.can));
        skins.add(BitmapFactory.decodeResource(getResources(), R.drawable.bush));
        recentlyIncreased = false;

        crowd = new ArrayList<>();
        toBeRemoved = new ArrayList<>();
        //crowd.add(new CrowdMember(skins.get(0), -75, 1000 - skins.get(0).getHeight()));
        rand = new Random();
        interval = 20 + rand.nextInt(4)*5;
        harvey = new CrowdMember(BitmapFactory.decodeResource(getResources(), R.drawable.dog), 150, 950);
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
                System.out.println("xPOS: " + harvey.xLocation + " yPOS: " + harvey.yLocation + " Score: " + score + " xVel: " + xVelocity + " yVel: " + yVelocity);
                lastDrawTime = System.currentTimeMillis();

                //updates Harvey for jumping
                harvey.yLocation -= yVelocity * elapsed;
                yVelocity -= acceleration * elapsed;
                if(harvey.yLocation > 950){
                    harvey.yLocation = 950;
                }

                //makes every crowd member move left at velocity xVelocity
                for (CrowdMember c : crowd) {
                    c.xLocation -= xVelocity * elapsed;
                }
                //spawns new crowd members based on random clock
                if (counter % interval == 0) {
                    nextSkin = rand.nextInt(skins.size());
                    crowd.add(new CrowdMember(skins.get(nextSkin), 1000, 1000 - skins.get(nextSkin).getHeight()));
                    interval = 20 + rand.nextInt(4) * 5;
                    counter = 0;
                }

                //increase difficulty as score increases
                if (score % 5 == 0 && !recentlyIncreased) {
                    xVelocity += .05;
                    //prevents difficulty from increasing more than once per each score milestone
                    recentlyIncreased = true;
                }

            }
            yLoc = height_scale * yPosition;
            xLoc = width_scale * xPosition;
            drawScore(canvas);
            for (CrowdMember c : crowd) {
                drawCrowd(canvas, c);
                if (c.xLocation < -150) {
                    toBeRemoved.add(c);
                }
                //collision detection
                if (harvey.yLocation == 950 && c.xLocation > (harvey.xLocation - (c.bitmap.getHeight() / 2)) && c.xLocation < (harvey.xLocation + (c.bitmap.getHeight() / 2))) {
                    gameOver = true;
                    System.out.println("---------- COLLISION!!! -------------");
                    context.gotoGameOver(score);
                }
            }
            drawCrowd(canvas, harvey);

            for (CrowdMember c : toBeRemoved) {
                score += 1;
                recentlyIncreased = false;
                crowd.remove(c);
            }
            toBeRemoved.clear();

            //changes which obstacles spawn as game goes on
            while (!spawnConditions[0] && score == 10){
                skins.add(BitmapFactory.decodeResource(getResources(), R.drawable.can_can));
                spawnConditions[0] = true;
            }
            while (!spawnConditions[1] && score == 20){
                skins.add(BitmapFactory.decodeResource(getResources(), R.drawable.bush_can));
                skins.add(BitmapFactory.decodeResource(getResources(), R.drawable.can_bush));
                spawnConditions[1] = true;
            }
            while (!spawnConditions[2] && score == 30){
                skins.add(BitmapFactory.decodeResource(getResources(), R.drawable.bush_bush));
                skins.remove(0);
                spawnConditions[2] = true;
            }
            while (!spawnConditions[3] && score == 40){
                skins.remove(0);
                spawnConditions[3] = true;
            }
            while (!spawnConditions[4] && score == 50){
                skins.remove(0);
                spawnConditions[4] = true;
            }

            this.invalidate();
        }
    }
    private void drawCrowd(Canvas canvas, CrowdMember member) {
        Paint crowdPaint = new Paint();
        canvas.drawBitmap(member.bitmap, member.xLocation * width_scale - (member.bitmap.getWidth() / 2), member.yLocation * height_scale, crowdPaint);
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
    public float getyVelocity(){ return yVelocity; }
    public void setyVelocity(float f){ yVelocity = f; }
    public float getHarveyyLocation() {return harvey.yLocation; }
}
