/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.util;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class MyAppConstant implements Serializable {

    public static class RemoveItemsFeatures {

        public static final String CART_PAGE = "cartPage";
    }

    public class LoginFeatures {

        public static final String INVALID_PAGE = "invalid";
        public static final String SEARCH_ACTION = "searchAction";
    }

    public class SearchAccountFeatures {

        public static final String SEARCH_PAGE = "searchPage";
    }

    public class ShoppingFeatures {

        public static final String SHOPPING_PAGE = "shopPage";
        public static final String SHOPPING_ACTION = "shopAction";
    }

    //    Admin
    public static class AdminFeatures {

        public static final String DASHBORAD_PAGE = "dashboardPage";
        public static final String INIT_ATTRIBUTE_ACTION = "initAttributeAction";
        public static final String PROFILE_PAGE = "adminProfilePage";
        public static final String EDIT_PROFILE_PAGE = "editAdProfilePage";
        public static final String ADD_PRODUCT_PAGE = "addNewProductPage";
        public static final String ADD_PRODUCT_ACTION = "addNewProductAction";
        public static final String ACCEPTED_PRODUCT_PAGE = "accpetedProductPage";
        public static final String VIEW_PRODUCT_PAGE = "viewProductDetailPage";
        public static final String EDIT_PRODUCT_PAGE = "editProductPage";
        public static final String PENDING_PRODUCT_PAGE = "pendingProductPage";
        public static final String REVENUE_BILLCOUNT = "dashboard";

//        User
        public static final String MANAGE_CUSTOMER_PAGE = "manageCustomerPage";
        public static final String MANAGE_SELLER_PAGE = "manageSellerPage";

        public static final String ACCEPTED_POST_PAGE = "activePostPage";
        public static final String PENDING_POST_PAGE = "nonActivePost";
        public static final String ADD_POST_ACTION = "addNewPostAction";
        public static final String ADD_POST_PAGE = "addPostPage";
        public static final String VIEW_POST_PAGE = "articlePage";
        public static final String EDIT_POST_PAGE = "editPostPage";

    }
}
