package com.rx7ru.onesmartkeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CustomKeyboardView extends View {
    private List<Key> keys;

    public CustomKeyboardView(Context context) {
        super(context);
        init();
    }

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        keys = new ArrayList<>();
        String[] letters = {
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                "A", "S", "D", "F", "G", "H", "J", "K", "L",
                "Z", "X", "C", "V", "B", "N", "M"
        };

        int rowCount = 5; // Количество строк клавиш
        int columnCount = 10; // Количество клавиш в ряду

        int keyWidth = getWidth() / columnCount;
        int keyHeight = getHeight() / rowCount;

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                int index = row * columnCount + column;
                if (index < letters.length) {
                    String letter = letters[index];
                    int x = column * keyWidth;
                    int y = row * keyHeight;

                    Key key = new Key(letter, x, y, keyWidth, keyHeight);
                    keys.add(key);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int desiredHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Key key : keys) {
            key.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                for (Key key : keys) {
                    if (key.contains(x, y)) {
                        key.setPressed(true);
                        invalidate();
                        break;
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
                for (Key key : keys) {
                    if (key.isPressed()) {
                        key.setPressed(false);
                        invalidate();
                        break;
                    }
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    private class Key {
        private String letter;
        private RectF rect;
        private boolean pressed;

        public Key(String letter, int x, int y, int width, int height) {
            this.letter = letter;
            this.rect = new RectF(x, y, x + width, y + height);
            this.pressed = false;
        }

        public void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(pressed ? Color.BLUE : Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(rect, paint);

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawRect(rect, paint);

            paint.setTextSize(40);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText(letter, rect.centerX(), rect.centerY() + 15, paint);
        }

        public boolean contains(float x, float y) {
            return rect.contains(x, y);
        }

        public boolean isPressed() {
            return pressed;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }
    }
}
