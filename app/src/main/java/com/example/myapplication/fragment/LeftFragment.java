package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.LeftAdapter;
import com.example.myapplication.bean.ItemDataBean;

import java.util.ArrayList;
import java.util.List;

public class LeftFragment extends Fragment implements LeftAdapter.ItemClickListener {

    private static final String TAG = "LeftFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public LeftFragment() {
    }

    public static LeftFragment newInstance(String param1, String param2) {
        LeftFragment fragment = new LeftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView recyclerViewLeft;
    private LeftAdapter leftAdapter;
    private List<ItemDataBean> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        recyclerViewLeft = view.findViewById(R.id.recyclerView_left);
        list = new ArrayList<>();
        ItemDataBean itemDataBean = new ItemDataBean(R.drawable.icon_my_wallet, "我的钱包", "钱包的Activity");
        list.add(itemDataBean);
        itemDataBean = new ItemDataBean(R.drawable.icon_my_journey, "我的行程", "行程的Activity");
        list.add(itemDataBean);
        itemDataBean = new ItemDataBean(R.drawable.icon_my_car, "我的车辆", "车辆的Activity");
        list.add(itemDataBean);
        itemDataBean = new ItemDataBean(R.drawable.icon_my_wallet, "我的数据", "数据的Activity");
        list.add(itemDataBean);
        itemDataBean = new ItemDataBean(R.drawable.icon_call_center, "客服中心", "客服的Activity");
        list.add(itemDataBean);
        itemDataBean = new ItemDataBean(R.drawable.icon_set, "设置", "设置的Activity");
        list.add(itemDataBean);
        itemDataBean = new ItemDataBean(R.drawable.car_service_order_icon, "车服务订单", "订单的Activity");
        list.add(itemDataBean);
        itemDataBean = new ItemDataBean(R.drawable.icon_my_wallet, "邀请乘客", "随便一个Activity");
        list.add(itemDataBean);
        leftAdapter = new LeftAdapter(getActivity(), list, this);

        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewLeft.setAdapter(leftAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void OnItemClickListener(int position, String activityName) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(getActivity(), clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "OnItemClickListener: " + activityName + "not exist");
            e.printStackTrace();
        }
    }

    @Override
    public void OnItemLongClickListener(int position) {

    }
}
