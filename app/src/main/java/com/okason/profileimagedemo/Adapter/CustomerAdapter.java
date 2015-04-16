package com.okason.profileimagedemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.okason.profileimagedemo.Model.Customer;
import com.okason.profileimagedemo.R;

import java.util.List;

/**
 * Created by Valentine on 4/16/2015.
 */
public class CustomerAdapter extends ArrayAdapter<Customer>{
    private Context mContext;
    private List<Customer> mCustomers;


    public CustomerAdapter(Context context, List<Customer> customers)
    {
        super(context, R.layout.row_customer_list, customers);
        context = mContext;
        mCustomers = customers;
    }



    @Override
    public int getCount() {
        return mCustomers.size();
    }

    @Override
    public Customer getItem(int position) {
        if (position < mCustomers.size()) {
            return mCustomers.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        private TextView Name;
        private TextView Email;
        private ImageView Thumbnail;
    }



    public void Add(Customer customer)
    {
        mCustomers.add(customer);
        this.notifyDataSetChanged();
    }

    public void Update()
    {
        mCustomers.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder mHolder;

        Customer customer= getItem(position);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.row_customer_list, null);

            mHolder = new Holder();
            mHolder.Name = (TextView) view.findViewById(R.id.textCustomerName);
            mHolder.Email = (TextView) view.findViewById(R.id.textCustomerEmail);
            mHolder.Thumbnail = (ImageView) view.findViewById(R.id.customer_image_thumbnail);

            view.setTag(mHolder);
        }else {
            mHolder = (Holder)view.getTag();
        }

        //Set the customer name
        if (mHolder.Name != null) {
            mHolder.Name.setText(customer.getName());
        }
        //Set the customer email
        if (mHolder.Email != null) {
            mHolder.Email.setText(customer.getEmailAddress());
        }

        //set the customer thumbnail
        if (mHolder.Thumbnail != null){
            mHolder.Thumbnail.setImageDrawable(customer.getThumbnail(getContext()));
        }
        return view;
    }




}
