package yincheng.sourcecodeinvestigate.ui;

import android.content.Context;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.view.stackview.CardAdapter;
import yincheng.sourcecodeinvestigate.view.stackview.StackCardView;

/**
 * Created by yincheng on 2018/4/28/14:17.
 * github:luoyincheng
 */
public class TestFramelayout extends FrameLayout {
    private final FrameLayout mRootView;
    private StackCardView stackCardView;
    private List<CardItemView> cardItemViews = new ArrayList<>();
    private List<CardItemData> cardItemDatas = new ArrayList<>();

    private String imagePaths[] = {"file:///android_asset/wall01.jpg",
            "file:///android_asset/wall02.jpg", "file:///android_asset/wall03.jpg",
            "file:///android_asset/wall04.jpg", "file:///android_asset/wall05.jpg",
            "file:///android_asset/wall06.jpg", "file:///android_asset/wall07.jpg",
            "file:///android_asset/wall08.jpg", "file:///android_asset/wall09.jpg",
            "file:///android_asset/wall10.jpg", "file:///android_asset/wall11.jpg",
            "file:///android_asset/wall12.jpg"}; // 12个图片资源

    private String names[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟"}; // 12个人名

    public TestFramelayout(@NonNull Context context) {
        this(context, null);
    }

    public TestFramelayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestFramelayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater layoutInflater = LayoutInflater.from(context);//有Context就有LayoutInflater
        mRootView = (FrameLayout) layoutInflater.inflate(R.layout.framelayout_test, null, true);
        stackCardView = mRootView.findViewById(R.id.stackbardview);
        addView(mRootView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);//在构造函数里面添加子View
        prepareDataList();// TODO: 2018/4/28 check
        stackCardView.setAdapter(new CardAdapter() {
            @Override
            public int getItemLayoutId() {
                Log.e("wodeshijie", "getItemLayoutId()");
                return R.layout.item_card;
            }

            @Override
            public int getCount() {
                Log.e("wodeshijie", "getCount()");
                return cardItemViews.size();
            }

            @Override
            public void bindView(View view, int index) {
                Log.e("wodeshijie", "bindView()");
                Object tag = view.getTag();// TODO: 2018/4/28 to un
                ViewHolder viewHolder;
                if (tag != null) {
                    viewHolder = (ViewHolder) tag;// TODO: 2018/4/28 to un
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);// TODO: 2018/4/28 to un
                }
                viewHolder.bindData(cardItemDatas.get(index));
            }

            @Override
            public Object getItemData(int index) {
                Log.e("wodeshijie", "getItemData()");
                return cardItemDatas.get(index);
            }

            @Override
            public RectF obtainDraggableArea(View view) {
                Log.e("wodeshijie", "obtainDraggableArea()");
                return super.obtainDraggableArea(view);
                // TODO: 2018/4/28
            }
        });
    }

    private void prepareDataList() {
        Log.e("wodeshijie", "prepareDataList()");
        for (int i = 0; i < 6; i++) {
            CardItemData dataItem = new CardItemData();
            dataItem.userName = names[i];
            dataItem.imagePath = imagePaths[i];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            cardItemDatas.add(dataItem);
        }
    }

    private void appendDataList() {
        for (int i = 0; i < 6; i++) {
            CardItemData dataItem = new CardItemData();
            dataItem.userName = "From Append";
            dataItem.imagePath = imagePaths[8];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            cardItemDatas.add(dataItem);
        }
    }

    class ViewHolder {
        ImageView imageView;
        View maskView;
        TextView userNameTv;
        TextView imageNumTv;
        TextView likeNumTv;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.card_image_view);
            maskView = view.findViewById(R.id.maskView);
            userNameTv = (TextView) view.findViewById(R.id.card_user_name);
            imageNumTv = (TextView) view.findViewById(R.id.card_pic_num);
            likeNumTv = (TextView) view.findViewById(R.id.card_like);
        }

        public void bindData(CardItemData itemData) {
            Glide.with(TestFramelayout.this).load(itemData.imagePath).into(imageView);
            userNameTv.setText(itemData.userName);
            imageNumTv.setText(itemData.imageNum + "");
            likeNumTv.setText(itemData.likeNum + "");
        }
    }
}