package com.example.humorme.util;


    public interface ItemTouchHelperAdapter{
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }

