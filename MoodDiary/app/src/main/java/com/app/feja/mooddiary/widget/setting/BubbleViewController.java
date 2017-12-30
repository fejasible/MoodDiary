package com.app.feja.mooddiary.widget.setting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Random;

/**
 * created by fejasible@163.com
 */
public class BubbleViewController {

    public static final String TAG = BubbleViewController.class.getSimpleName();

    private World world;
    private float dt = 1f / 60f;
    private int velocityIterations = 3;
    private int positionIterations = 10;
    private float friction = 0.3f, density = 0.5f, restitution = 0.3f, ratio = 50;
    private float gravity = 10f;
    private int width, height;
    private boolean enable = true;
    private final Random random = new Random();

    private ViewGroup viewGroup;

    public BubbleViewController(ViewGroup viewgroup) {
        this.viewGroup = viewgroup;
        density = viewgroup.getContext().getResources().getDisplayMetrics().density;
    }

    public void onSizeChanged(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private Vec2 vec2 = new Vec2();
    private Paint paint;

    public void onDraw(Canvas canvas) {
        if (paint == null) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.LTGRAY);
            paint.setStrokeWidth(2);
        }
        if (!enable) {
            return;
        }
        world.step(dt, velocityIterations, positionIterations);
        int childCount = viewGroup.getChildCount();
        boolean peace = true;
        boolean takePlace = true;
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body != null) {
                view.setX(metersToPixels(body.getPosition().x) - view.getWidth() / 2);
                view.setY(metersToPixels(body.getPosition().y) - view.getHeight() / 2);
                view.setRotation(radiansToDegrees(body.getAngle() % 360));

                if (body.getLinearVelocity().x != 0 || body.getLinearVelocity().y != 0) {
                    peace = false;
                }
            }
            float dx = pixelsToMeters(width) / (childCount + 1);
            if (body != null) {
                if (Math.abs(body.getPosition().x - dx * (i + 1)) > dx / 4
                        && body.getLinearVelocity().x == 0
                        && body.getLinearVelocity().y == 0) {
                    float x = (body.getPosition().x > dx * (i + 1) ? -1f : 1f) * (random.nextFloat() * 0.9f + 0.1f);
                    float y = -(random.nextFloat() * 5 + 1f);
                    body.applyLinearImpulse(vec2.set(x, y), body.getPosition(), true);
                    takePlace = false;
                }
            }
        }
        if (peace && takePlace) {
            float dx = pixelsToMeters(width) / (childCount + 1);
            for (int i = 0; i < childCount; i++) {
                View view = viewGroup.getChildAt(i);
                Body body = (Body) view.getTag(R.id.mobike_body_tag);
                if (body != null) {
                    body.applyLinearImpulse(vec2.set(dx * (i + 1) - body.getPosition().x, 0), body.getPosition(), true);
                }
            }
        }
        canvas.drawLine(0, height / 2, width, height / 2, paint);
        viewGroup.invalidate();
    }

    public void onLayout(boolean changed) {
        createWorld(changed);
    }

    public void onStart() {
        setEnable(true);
    }

    public void onStop() {
        setEnable(false);
    }

    public void update() {
        world = null;
        onLayout(true);
    }

    private void createWorld(boolean changed) {
        if (world == null) {
            world = new World(new Vec2(0, gravity));
            createTopAndBottomBounds();
            createLeftAndRightBounds();
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);

            if (body == null || changed) {
                body = createBody(world, view);
                body.setLinearVelocity(new Vec2(random.nextFloat() + 30f * i / childCount, random.nextFloat()*7));
            }
        }
    }

    public void onViewClick() {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body != null) {
                body.setLinearVelocity(new Vec2(random.nextFloat() * 10 - 5, random.nextFloat() * 20 - 18));
            }
        }
    }

    private Body createBody(World world, View view) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.setType(BodyType.DYNAMIC);

        bodyDef.position.set(
                pixelsToMeters(view.getX() + view.getWidth() / 2),
                pixelsToMeters(view.getY() + view.getHeight() / 2));
        Shape shape;
        Boolean isCircle = (Boolean) view.getTag(R.id.mobike_view_circle_tag);
        if (isCircle != null && isCircle) {
            shape = createCircleShape(view);
        } else {
            shape = createPolygonShape(view);
        }
        FixtureDef fixture = new FixtureDef();
        fixture.setShape(shape);
        fixture.friction = friction;
        fixture.restitution = restitution;
        fixture.density = density;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixture);
        view.setTag(R.id.mobike_body_tag, body);
        body.setFixedRotation(true);
        return body;
    }

    private Shape createCircleShape(View view) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(pixelsToMeters(view.getWidth() / 2));
        return circleShape;
    }

    private Shape createPolygonShape(View view) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(pixelsToMeters(view.getHeight() / 3), pixelsToMeters(view.getHeight() / 3));
        return polygonShape;
    }

    private void createTopAndBottomBounds() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();
        float boxWidth = pixelsToMeters(width);
        float boxHeight = pixelsToMeters(ratio);
        box.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;

        bodyDef.position.set(0, -boxHeight);
        Body topBody = world.createBody(bodyDef);
        topBody.createFixture(fixtureDef);

        bodyDef.position.set(0, pixelsToMeters(height / 2) + boxHeight);
        Body bottomBody = world.createBody(bodyDef);
        bottomBody.createFixture(fixtureDef);
    }

    private void createLeftAndRightBounds() {
        float boxWidth = pixelsToMeters(ratio);
        float boxHeight = pixelsToMeters(height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();
        box.setAsBox(boxWidth, boxHeight);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;

        bodyDef.position.set(-boxWidth, boxHeight);
        Body leftBody = world.createBody(bodyDef);
        leftBody.createFixture(fixtureDef);


        bodyDef.position.set(pixelsToMeters(width) + boxWidth, 0);
        Body rightBody = world.createBody(bodyDef);
        rightBody.createFixture(fixtureDef);
    }

    private float radiansToDegrees(float radians) {
        return radians / 3.14f * 180f;
    }

    private float degreesToRadians(float degrees) {
        return (degrees / 180f) * 3.14f;
    }

    public float metersToPixels(float meters) {
        return meters * ratio;
    }

    public float pixelsToMeters(float pixels) {
        return pixels / ratio;
    }

    public void random() {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Vec2 impulse = new Vec2(random.nextInt(1000) - 1000, random.nextInt(1000) - 1000);
            View view = viewGroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body != null) {
                body.applyLinearImpulse(impulse, body.getPosition(), true);
            }
        }
    }

    public void onSensorChanged(float x, float y) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Vec2 impulse = new Vec2(x, y);
            View view = viewGroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body != null) {
                body.applyLinearImpulse(impulse, body.getPosition(), true);
            }
        }
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        if (friction >= 0) {
            this.friction = friction;
        }
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        if (density >= 0) {
            this.density = density;
        }
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        if (restitution >= 0) {
            this.restitution = restitution;
        }
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        if (ratio >= 0) {
            this.ratio = ratio;
        }
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        viewGroup.invalidate();
    }
}