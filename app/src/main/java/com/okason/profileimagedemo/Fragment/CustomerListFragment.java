package com.okason.profileimagedemo.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.okason.profileimagedemo.Adapter.CustomerAdapter;
import com.okason.profileimagedemo.Data.DatabaseHelper;
import com.okason.profileimagedemo.Helpers.Constants;
import com.okason.profileimagedemo.Helpers.Enums;
import com.okason.profileimagedemo.MainActivity;
import com.okason.profileimagedemo.Model.Customer;
import com.okason.profileimagedemo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link com.okason.profileimagedemo.Fragment.CustomerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerListFragment extends Fragment {

    private List<Customer> mCustomers;
    private ListView mCustomerListView;
    private CustomerAdapter mAdapter;
    private View mRootView;
    private DatabaseHelper db;

    public static CustomerListFragment newInstance(int sectionNumber) {
        CustomerListFragment fragment = new CustomerListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public CustomerListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = new DatabaseHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_customer_list, container, false);

        InitializeViews();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadListData();
    }

    private void LoadListData()
    {
        //First clear the adapter of any Customers it has
        mAdapter.Update();

        //Get the list of customers from the database
        mCustomers = db.getAllCustomers();

        if (mCustomers != null){
            for (Customer customer: mCustomers){
                mAdapter.add(customer);
            }
        }

    }

    private void InitializeViews() {

        mCustomerListView = (ListView) mRootView.findViewById(R.id.customer_list);
        mCustomers = new ArrayList<Customer>();
        mAdapter = new CustomerAdapter(getActivity(), mCustomers);
        mCustomerListView.setAdapter(mAdapter);
        TextView emptyText = (TextView) mRootView.findViewById(R.id.client_list_empty);
        mCustomerListView.setEmptyView(emptyText);
        mCustomerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the selected client
                Customer tempCustomer = mCustomers.get(position);
                MainActivity myActivity = (MainActivity)getActivity();
                    //go to edit the selected client
                    myActivity.ReplaceFragment(Enums.FragmentEnums.CustomerDetailsFragment, 3, tempCustomer.getId());

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.customer_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_add_customer:
                MainActivity myActivity = (MainActivity)getActivity();
                myActivity.ReplaceFragment(Enums.FragmentEnums.CustomerDetailsFragment, 3, 0);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(Constants.ARG_SECTION_NUMBER));
    }



}
