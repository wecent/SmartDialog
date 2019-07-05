package com.wecent.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wecent.smartdialog.SmartDialog;
import com.wecent.smartdialog.callback.ConfigDialog;
import com.wecent.smartdialog.callback.ConfigItems;
import com.wecent.smartdialog.params.DialogParams;
import com.wecent.smartdialog.params.ItemsParams;
import com.wecent.smartdialog.resource.values.SmartDimen;
import com.wecent.smartdialog.widget.listener.OnItemsClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    //全局配置
    static {
        SmartDimen.DIALOG_RADIUS = 20;
    }

    private SmartDialog.Builder builder;
    private DialogFragment dialogFragment;
    private Handler handler;
    private Runnable runnable;
    private int time = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.wecent.smartdialog.sample.R.layout.activity_main);

        RecyclerView recyclerView = findViewById(com.wecent.smartdialog.sample.R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> listData = Arrays.asList(
                "提示弹框（单一按钮）",
                "提示弹框（两个按钮）",
                "提示弹框（三个按钮）",
                "输入弹框",
                "单选弹框（居中弹出）",
                "单选弹框（底部弹出）",
                "单选弹框（底部弹出）",
                "进度弹框",
                "单选弹框(表格样式)"
//                "换头像",
//                "输入框",
//                "进度框",
//                "等待框",
//                "动态改变内容",
//                "自定义dialog",
//                "list中使用",
//                "倒计时",
//                "自定义List adapter(多选)",
//                "Rv换头像",
//                "自定义Rv adapter",
//                "自定义List adapter(单选)",
//                "自定义内容视图",
//                "lottie动画框"
        );
        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1
                , listData) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
//        ScaleLayoutConfig.init(this.getApplicationContext(),480,800);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                new SmartDialog.Builder()
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.radius = 28;
                            }
                        })
                        .setWidth(0.8f)
                        .setTitle("标题")
                        .setMessage("这是一条来着未来的消息")
                        .setPositive("确定", null)
                        .configMessage(params ->
                            params.padding = new int[]{100, 0, 100, 50})
//                        .setOnShowListener(dialog ->
//                                Toast.makeText(MainActivity.this, "显示了！", Toast.LENGTH_SHORT).show())
//                        .setOnCancelListener(dialog ->
//                                Toast.makeText(MainActivity.this, "取消了！", Toast.LENGTH_SHORT).show())
                        .show(getSupportFragmentManager());
                break;
            case 1:
                new SmartDialog.Builder()
                        .setTitle("标题")
                        .setSubtitle("副标题")
                        .setMessage("这是一条来着未来的消息")
                        .configMessage(params ->
                            params.padding = new int[]{100, 0, 100, 50})
                        .setNegative("取消", null)
                        .setPositive("确定", v ->
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show())
                        .show(getSupportFragmentManager());
                break;
            case 2:
                new SmartDialog.Builder()
                        .setTitle("标题")
                        .setSubtitle("副标题")
                        .setMessage("这是一条来着未来的消息")
                        .setOnCreateMessageListener(message -> message.setText("重新设置对话框内容"))
                        .setNegative("取消", v ->
                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show())
                        .setNeutral("中间", v ->
                                Toast.makeText(MainActivity.this, "中间", Toast.LENGTH_SHORT).show())
                        .setPositive("确定", v ->
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show())
                        .show(getSupportFragmentManager());
                break;
            case 3:
                dialogFragment = new SmartDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setInputManualClose(true)
                        .setTitle("标题")
                        .setSubtitle("副标题")
                        .setInputHint("请输入条件")
                        .autoInputShowKeyboard()
                        .configInput(params -> {
                            params.padding = new int[]{20, 30, 20, 30};
                            params.margins = new int[]{30, 0, 30, 50};
                            params.inputHeight = 120;
                        })
                        .setNegative("取消", null)
                        .setPositiveInput("确定", (text, v) -> {
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(MainActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            case 4:
                final String[] items1 = {"拍照", "相册", "视频"};
                new SmartDialog.Builder()
                        .configDialog(params -> {
                            params.gravity = Gravity.CENTER_VERTICAL;
                        })
                        .setTitle("标题")
                        .setSubtitle("请从以下中选择照片的方式进行提交")
                        .setItems(items1, new OnItemsClickListener() {
                            @Override
                            public void onItemsClick(View view, int position) {
                                Toast.makeText(MainActivity.this, "点击了：" + items1[position], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            case 5:
                final String[] items2 = {"拍照", "相册", "视频"};
                new SmartDialog.Builder()
                        .configDialog(params -> {
                            params.gravity = Gravity.BOTTOM;
                            //增加弹出动画
                            params.animStyle = com.wecent.smartdialog.sample.R.style.dialogWindowAnim;
                        })
                        .setTitle("标题")
                        .setSubtitle("请从以下中选择照片的方式进行提交")
                        .configItems(new ConfigItems() {
                            @Override
                            public void onConfig(ItemsParams params) {
                                params.bottomMargin = 0;
                            }
                        })
                        .setItems(items2, new OnItemsClickListener() {
                            @Override
                            public void onItemsClick(View view, int position) {
                                Toast.makeText(MainActivity.this, "点击了：" + items2[position], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            case 6:
                final String[] items3 = {"拍照", "相册", "视频"};
                new SmartDialog.Builder()
                        .configDialog(params -> {
                            //增加弹出动画
                            params.animStyle = com.wecent.smartdialog.sample.R.style.dialogWindowAnim;
                        })
                        .setTitle("标题")
                        .setSubtitle("请从以下中选择照片的方式进行提交")
                        .setItems(items3, new OnItemsClickListener() {
                            @Override
                            public void onItemsClick(View view, int position) {
                                Toast.makeText(MainActivity.this, "点击了：" + items3[position], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegative("取消", v -> {
                            Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        })
                        .show(getSupportFragmentManager());
                break;
            case 7:
                final Timer timer = new Timer();
                builder = new SmartDialog.Builder();
                builder.setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setTitle("下载")
                        .setProgressText("已经下载")
                        .setNegative("取消", v -> timer.cancel())
                        .show(getSupportFragmentManager());
                TimerTask timerTask = new TimerTask() {
                    final int max = 222;
                    int progress = 0;
                    @Override
                    public void run() {
                        progress++;
                        if (progress > max) {
                            MainActivity.this.runOnUiThread(() -> {
                                builder.setProgressText("下载完成")
                                        .setNegative("确定", v -> timer.cancel())
                                        .create();
                                timer.cancel();
                            });
                        } else {
                            builder.setProgress(max, progress).create();
                        }
                    }
                };
                timer.schedule(timerTask, 0, 50);
                break;
            case 8:
                final List<String> list = new ArrayList<>();
                list.add("拍照");
                list.add("相册");
                list.add("视频");
                list.add("其他");
                list.add("其他");
                new SmartDialog.Builder()
                        .setItems(list, (view13, position13) ->
                                Toast.makeText(MainActivity.this, "点击了：" + list.get(position13), Toast.LENGTH_SHORT).show())
                        .setNegative("取消", null)
                        .show(getSupportFragmentManager());
                break;

//            case 6:
//                dialogFragment = new SmartDialog.Builder()
//                        .setProgressText("登录中...")
//                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressDrawable(R.drawable.bg_progress_s)
//                        .show(getSupportFragmentManager());
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialogFragment.dismiss();
//                    }
//                }, 3000);
//                break;
//            case 7:
//                builder = new SmartDialog.Builder();
//                builder.configDialog(params -> {
//                    params.gravity = Gravity.TOP;
////                        TranslateAnimation refreshAnimation = new TranslateAnimation(15, -15,
//// 0, 0);
////                        refreshAnimation.setInterpolator(new OvershootInterpolator());
////                        refreshAnimation.setDuration(100);
////                        refreshAnimation.setRepeatCount(3);
////                        refreshAnimation.setRepeatMode(Animation.RESTART);
//                    params.refreshAnimation = com.wecent.smart.sample.R.anim.refresh_animation;
//                })
//
//                        .setTitle("动态改变内容")
//                        .setMessage("3秒后更新其它内容")
//                        .setOnDismissListener(dialog -> removeRunnable())
//                        .show(getSupportFragmentManager());
//                handler = new Handler();
//                runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        builder.setMessage("已经更新内容").create();
//                    }
//                };
//                handler.postDelayed(runnable, 3000);
//                break;
//            case 8:
////                DialogLoginConnPc.getInstance().show(getSupportFragmentManager(), "connPc");
//                CustomDialog.getInstance().show(getSupportFragmentManager(), "CustomDialog");
//                break;
//            case 9:
//                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
//                break;
//            case 10:
//                builder = new SmartDialog.Builder()
//                        .setTitle("标题")
//                        .setMessage("提示框")
//                        .configPositive(params -> params.disable = true)
//                        .setPositive("确定(" + time + "s)", null)
//                        .setNegative("取消", null);
//                builder.setOnDismissListener(dialog -> removeRunnable());
//                dialogFragment = builder.show(getSupportFragmentManager());
//                handler = new Handler();
//                runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        builder.configPositive(params -> {
//                            --time;
//                            params.text = "确定(" + time + "s)";
//                            if (time == 0) {
//                                params.disable = false;
//                                params.text = "确定";
//                            }
//                        }).create();
//
//                        if (time == 0)
//                            handler.removeCallbacks(this);
//                        else
//                            handler.postDelayed(this, 1000);
//
//                    }
//                };
//                handler.postDelayed(runnable, 1000);
//                break;
//            case 11:
//                final String[] objects = {"item0", "item1", "item2", "item3"};
//                final CheckedAdapter checkedAdapter = new CheckedAdapter(this, objects);
//
//                new SmartDialog.Builder()
//                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
//                        .setTitle("带复选的ListView")
//                        .setSubtitle("可多选")
//                        .setItems(checkedAdapter, (parent, view12, position12, id) ->
//                                checkedAdapter.toggle(position12, objects[position12]))
//                        .setItemsManualClose(true)
//                        .setPositive("确定", v -> Toast.makeText(MainActivity.this
//                                , "选择了：" + checkedAdapter.getSaveChecked().toString()
//                                , Toast.LENGTH_SHORT).show())
//                        .show(getSupportFragmentManager());
//                break;
//            case 12:
//                final List<PictureTypeEntity> list = new ArrayList<>();
//                list.add(new PictureTypeEntity(1, "拍照"));
//                list.add(new PictureTypeEntity(2, "相册"));
//                list.add(new PictureTypeEntity(3, "视频"));
//                list.add(new PictureTypeEntity(4, "其他"));
//                list.add(new PictureTypeEntity(5, "其他"));
//                list.add(new PictureTypeEntity(6, ""));
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
//                new SmartDialog.Builder()
//                        .setTitle("标题")
//                        .setSubtitle("请从以下中选择照片的方式进行提交")
//                        .configItems(params -> {
//                            params.dividerHeight = 0;
//                            params.bottomMargin = 20;})
//                        .setItems(list, gridLayoutManager, (view13, position13) ->
//                                Toast.makeText(MainActivity.this, "点击了：" + list.get(position13)
//                                        , Toast.LENGTH_SHORT).show())
//                        .setNegative("取消", null)
//                        .show(getSupportFragmentManager());
//                break;
//            case 13:
//                String[] heads = {"Head1", "Head2"};
//                ArrayList<MySectionEntity> listData = new ArrayList<>();
//                for (int i = 0; i < heads.length; i++) {
//                    listData.add(new MySectionEntity(true, heads[i]));
//                    for (int j = 0; j < (i == 0 ? 4 : 6); j++) {
//                        listData.add(new MySectionEntity(new PictureTypeEntity(j, heads[i] + "：" + j)));
//                    }
//                }
//                final BaseQuickAdapter rvAdapter = new BaseSectionQuickAdapter<MySectionEntity, BaseViewHolder>(
//                        android.R.layout.simple_list_item_1, com.wecent.smart.sample.R.layout.item_main_text, listData) {
//                    @Override
//                    protected void convertHead(BaseViewHolder helper, MySectionEntity item) {
//                        helper.setText(com.wecent.smart.sample.R.id.textView2, item.header);
//                    }
//
//                    @Override
//                    protected void convert(BaseViewHolder helper, MySectionEntity item) {
//                        TextView textView = helper.getView(android.R.id.text1);
//                        textView.setText(item.t.typeName);
//                        textView.setGravity(Gravity.CENTER);
//                    }
//
//                };
//                dialogFragment = new SmartDialog.Builder()
//                        .setGravity(Gravity.BOTTOM)
//                        .setRadius(0)
//                        .setWidth(1f)
//                        .setMaxHeight(0.8f)
//                        .setTitle("rvAdapter")
//                        .setSubtitle("副标题哦！")
//                        .setItems(rvAdapter, new LinearLayoutManager(this))
//                        .setNegative("关闭", null)
//                        .configNegative(params -> params.topMargin = 0)
//                        .show(getSupportFragmentManager());
//                rvAdapter.setOnItemClickListener((adapter1, view14, position14) -> {
//                    Toast.makeText(MainActivity.this, "点击的是：" + adapter1.getData().get(position14), Toast.LENGTH_SHORT).show();
//                    dialogFragment.dismiss();
//                });
//                break;
//            case 14:
//                final String[] objectsR = {"item0", "item1", "item2", "item3"};
//                final CheckedAdapter checkedAdapterR = new CheckedAdapter(this, objectsR, true);
//
//                new SmartDialog.Builder()
//                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
//                        .setTitle("带复选的ListView")
//                        .setSubtitle("单选")
//                        .setItems(checkedAdapterR, (parent, view15, position15, id) ->
//                                checkedAdapterR.toggle(position15, objectsR[position15]))
//                        .setItemsManualClose(true)
//                        .setPositive("确定", v -> Toast.makeText(MainActivity.this
//                                , "选择了：" + checkedAdapterR.getSaveChecked().toString()
//                                , Toast.LENGTH_SHORT).show())
//                        .show(getSupportFragmentManager());
//                break;
//            case 15:
//
//                dialogFragment = new SmartDialog.Builder()
//                        .setTitle("提示")
//                        .setWidth(0.7f)
//                        .setBodyView(R.layout.share_page_loading, view16 -> {
//                            SmartDrawable bgCircleDrawable = new SmartDrawable(SmartColor.DIALOG_BACKGROUND
//                                    , 0, 0, SmartDimen.DIALOG_RADIUS, SmartDimen.DIALOG_RADIUS);
//                            view16.setBackgroundDrawable(bgCircleDrawable);
//                        })
//                        .show(getSupportFragmentManager());
//                break;
//            case 16:
//                new SmartDialog.Builder()
//                        .setTitle("提示")
//                        .setSubtitle("副提示语")
//                        .setWidth(0.7f)
//                        .setLottieAnimation("loading.json")
//                        .setLottieLoop(true)
//                        .playLottieAnimation()
//                        .setLottieText("正在加载...")
//                        .show(getSupportFragmentManager());
//                break;
        }
    }

    private void removeRunnable() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        handler = null;
        runnable = null;
    }
}
