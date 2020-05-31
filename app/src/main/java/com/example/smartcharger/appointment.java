package com.example.smartcharger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class appointment extends AppCompatActivity {
    private TextView electric_pile_state;
    private TextView count_time;
    private Button start_btn;
    private Button stop_btn;
    private int num;//用于做几号机子的判断

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //初始化状态判断
        init_State();
        //初始化控件
        init_View();
        //设置计时函数，倒计时5分钟
        final CountDownTimer timer = new CountDownTimer(5 * 60 * 1000, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                start_btn.setEnabled(false);
                start_btn.setText(String.format("预约中"));
                electric_pile_state.setText("预约中");
                count_time.setText(String.format("倒计时%ds", millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                //倒计时结束后会触发的动作
                start_btn.setText("再次开始预约");
                start_btn.setEnabled(true);
                count_time.setText("计时完成");
                electric_pile_state.setText("可预约");
                cancel();//取消倒计时，防止内存泄漏
            }
        };
        //设置按钮单击事件
        //开始按钮
        start_btn.setOnClickListener(new View.OnClickListener() {
            //开始预约的点击事件，倒计时60s
            @Override
            public void onClick(View v) {
                num=Appoint_state.getState();
                if (num==0) {
                    //num=0表示
                    Appoint_state.set_state_one();//1号机正在预约中
                    Toast.makeText(appointment.this, "预约成功", Toast.LENGTH_SHORT).show();
                    timer.start();
                }
                else if (num==2){
                    Toast.makeText(appointment.this, "预约失败，已有他人预约", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //停止按钮
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=Appoint_state.set_state_zero();//释放预约资源
                Toast.makeText(appointment.this, "预约结束", Toast.LENGTH_SHORT).show();
                timer.onFinish();
            }
        });
    }

    private void init_View(){
        //初始化图层和控件
        electric_pile_state=findViewById(R.id.electric_pile_state);
        start_btn=findViewById(R.id.start_appoint);
        stop_btn=findViewById(R.id.stop_appoint);
        count_time=findViewById(R.id.counttime);
    }

    private void init_State(){
        //初始化状态判断的实例
        Appoint_state state=new Appoint_state();
    }

    //通过重写onKeyDown,配合singleInstance启动模式来实现返回时活动依然运行
    //其中，moveTaskToBack(true);这一项挺重要的，当输入false时，只对MainActivity(即只是栈底活动)生效，true则对所有的活动生效。
    //　　这样，通过将A和B分在两个任务栈，在B中按下返回时就能不被销毁，重新进入数据依然保留着。
    public  boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode== KeyEvent.KEYCODE_BACK)
        {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

}
