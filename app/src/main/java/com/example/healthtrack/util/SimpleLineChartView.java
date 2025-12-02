package com.example.healthtrack.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.util.AttributeSet;

import android.view.View;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A custom view to draw a simple line chart for the report.
 * Draw data points and connects them with lines.
 */
public class SimpleLineChartView extends View {
    private Paint linePaint;
    private Paint pointPaint;
    private Paint textPaint;
    private List<Float> dataPoints = new ArrayList<>();
    private List<String> labels = new ArrayList<>();

    public SimpleLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5f);
        linePaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER); // 文字居中
    }

    public void setData(List<Float> data, List<String> dateLabels) {
        // 数据拷贝，防止外部修改导致崩溃
        this.dataPoints = new ArrayList<>(data);
        this.labels = new ArrayList<>(dateLabels);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataPoints == null || dataPoints.isEmpty()) return;

        float width = getWidth();
        float height = getHeight();
        float padding = 60f;

        float graphHeight = height - (2 * padding);

        // calculate maxVal and minVal
        float maxVal = Collections.max(dataPoints);
        float minVal = Collections.min(dataPoints);

        // 如果只有一个点，或者最大最小值相等，为了防止除以0，人为制造一个范围
        if (maxVal == minVal) {
            maxVal += 10;
            minVal -= 10;
        }

        int size = dataPoints.size();
        // 如果只有1个点，我们将它画在中间；如果有多个点，计算间距
        float xGap = (size > 1) ? (width - 2 * padding) / (size - 1) : 0;

        for (int i = 0; i < size; i++) {
            float val = dataPoints.get(i);

            // 1. 计算 X 坐标
            float x;
            if (size == 1) {
                x = width / 2; // 只有一个点时，居中显示
            } else {
                x = padding + (i * xGap);
            }

            // 2. 计算 Y 坐标
            // 公式：y = 底部Y - (数值比例 * 图表高度)
            float y = (height - padding) - ((val - minVal) / (maxVal - minVal) * graphHeight);

            // 3. 绘制点
            canvas.drawCircle(x, y, 12f, pointPaint);

            // 4. 绘制数值 (在点上方)
            canvas.drawText(String.valueOf(val), x, y - 25, textPaint);

            // 5. 绘制日期 (在底部)
            if (i < labels.size()) {
                canvas.drawText(labels.get(i), x, height - 15, textPaint);
            }

            // 6. 绘制连线 (画到下一个点)
            if (i < size - 1) {
                float nextVal = dataPoints.get(i + 1);
                float nextX = padding + ((i + 1) * xGap);
                float nextY = (height - padding) - ((nextVal - minVal) / (maxVal - minVal) * graphHeight);
                canvas.drawLine(x, y, nextX, nextY, linePaint);
            }
        }
    }
}
