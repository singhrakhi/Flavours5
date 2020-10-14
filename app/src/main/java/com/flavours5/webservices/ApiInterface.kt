package com.flavours5.webservices

import com.flavours5.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
//
    @GET("Login")
    fun callLogin(
        @Query("username") username: String?,
        @Query("password") password: String?
    ): Call<LoginResponse?>?

    @GET("Registration")
    fun callSignup(
        @Query("username") username: String?,
        @Query("password") password: String?
    ): Call<SignupResponse?>?

    @GET("LoginWithOTP")
    fun callLoginWithOtp(
        @Query("username") username: String?
    ): Call<OtpVerifyResponse?>?

    @GET("VerifyLoginWithOTP")
    fun callVerifyOtp(
        @Query("username") username: String?,
        @Query("OTP") OTP: String?
    ): Call<LoginResponse?>?

    @GET("ResendOTPForLoginWithOTP")
    fun callResendOtp(
        @Query("username") username: String?
    ): Call<OtpVerifyResponse?>?

    @GET("ResendOTP")
    fun callResendForSignupOtp(
        @Query("username") username: String?
    ): Call<OtpVerifyResponse?>?

    @GET("VerifyOTP")
    fun callVerifySignupOtp(
        @Query("username") username: String?,
        @Query("OTP") OTP: String?
    ): Call<LoginResponse?>?

    @GET("Category")
    fun callCategoryData(): Call<CategoryResponse?>?

    @GET("Banner")
    fun callSliderData(): Call<BannerResponse?>?

    @GET("OfferList")
    fun callOfferData(): Call<OfferResponse?>?

    @GET("SubCategory")
    fun callSubCat(
        @Query("CategoryId") CategoryId: Int?
    ): Call<SubCategoryResponse?>?

    @GET("ProductListBySubcategory")
    fun callProducts(
        @Query("SubCategoryId") CategoryId: Int?
    ): Call<ProductResponse?>?

    @GET("AddToKart")
    fun callAddToCart(
        @Query("Mobile") Mobile : String,
        @Query("ProductId") productId: Int): Call<AddToCartResponse?>?

    @GET("UserKartDetails")
    fun callCartList(
        @Query("Mobile") Mobile : String): Call<CartDetailsResponse?>?

    @GET("deliverycharge")
    fun getDeliveryCharge(): Call<DeliveryChargeData?>?

    @GET("KartPlus")
    fun callCartPlus(
        @Query("Mobile") Mobile : String,
        @Query("ProductId") ProductId: Int): Call<AddToCartResponse?>?

    @GET("KartMinus")
    fun callCartMinus(
        @Query("Mobile") Mobile : String,
        @Query("ProductId") ProductId: Int): Call<AddToCartResponse?>?

    @GET("LocalityData")
    fun callLocalityData(): Call<LocalityResponse?>?

    @FormUrlEncoded
    @POST("AddAddress")
    fun addAddress(
        @Field("Userid") Userid: String?,
        @Field("DeliveryPersonName") DeliveryPersonName: String?,
        @Field("DeliveryPersonMOB") DeliveryPersonMOB: String?,
        @Field("mainAddress") mainAddress: String?,
        @Field("Pincode") Pincode: String?,
        @Field("Statename") Statename: String?,
        @Field("City") City: String?,
        @Field("Landmark") Landmark: String?,
        @Field("Locality") Locality: String?,
        @Field("ID") ID: String?
    ): Call<AddressResponse?>?

    @GET("UserAddressList")
    fun callGetAddressList(
        @Query("Mobile") Mobile : String
    ): Call<MyAddressResponse?>?

    @GET("DelUserAddress")
    fun deleteAddress(@Query("Id") Id: String?): Call<AddressResponse?>?

    @GET("EcommerceOrderDateList")
    fun callEcomDateList(): Call<DateResponse?>?

    @GET("GetDateDataByDateInEcommerce")
    fun callEomTimeList(
        @Query("Date") Date: String?
    ): Call<TimeResponse?>?

    @GET("SearchProduct")
    fun callSearchProducts(
        @Query("Search") Search: String?
    ): Call<ProductResponse?>?

    @FormUrlEncoded
    @POST("Checkout")
    fun makeOrder(
        @Field("Username") Username: String?,
        @Field("DeliveryPersonName") DeliveryPersonName: String?,
        @Field("DeliveryPersonMOB") DeliveryPersonMOB: String?,
        @Field("paymentMethod") paymentMethod: String?,
        @Field("OrderTotal") OrderTotal: String?,
        @Field("Deleverydate") Deleverydate: String?,
        @Field("Deleverytime") Deleverytime: String?,
        @Field("DeliveryType") DeliveryType: String?,
        @Field("SpecialIntraction") SpecialIntractio: String?,
        @Field("AddressId") AddressId: String?,
        @Field("DeliveryCharge") DeliveryCharge: Double
    ): Call<OrderResponse?>?

    @GET("UserOrderList")
    fun callMyOrder(
        @Query("Username") Username : String): Call<MyOrderResponse?>?

    @GET("ViewOrderDetails")
    fun callOrderDetails(
        @Query("OrderId") OrderId : String): Call<OrderDetailsResponse?>?



    @FormUrlEncoded
    @POST("BookGroceryTruck")
    fun bookTruck(
        @Field("ID") ID: String?,
        @Field("Name") Name: String?,
        @Field("Address") Address: String?,
        @Field("Mobilenumber") Mobilenumber: String?,
        @Field("createdate") createdate: String?,
        @Field("Message") Message: String?,
        @Field("Userid") Userid: String?,
        @Field("Date") Date: String?,
        @Field("Time") Time: String?,
        @Field("BookingId") BookingId: String?
    ): Call<OtpVerifyResponse?>?


    @FormUrlEncoded
    @POST("VerifyGroceryTruckBooking")
    fun verifyOtp(
        @Field("ID") ID: String?,
        @Field("Name") Name: String?,
        @Field("Address") Address: String?,
        @Field("Mobilenumber") Mobilenumber: String?,
        @Field("createdate") createdate: String?,
        @Field("Message") Message: String?,
        @Field("Userid") Userid: String?,
        @Field("Date") Date: String?,
        @Field("Time") Time: String?,
        @Field("BookingId") BookingId: String?,
        @Field("OTP") Otp: String?
    ): Call<OtpVerifyResponse?>?

    @GET("GetAllDateSlotOfGrocery")
    fun callGroceryDateList(): Call<GroceryDateResponse?>?

    @GET("GetAllTimeSlotOfGrocery")
    fun callGroceryTimeList(
        @Query("Id") Id: String?
    ): Call<GroceryTimeResponse?>?

    @GET("ResendOTPGroceryTruck")
    fun callResendOtpForGroceryTruck(
        @Query("MobileNumber") MobileNumber: String?
    ): Call<ResendOtpResponse?>?

    @FormUrlEncoded
    @POST("Changepassword")
    fun callChangepass(
        @Field("Mobile") Mobile: String?,
        @Field("OldPassword") OldPassword: String?,
        @Field("NewPassword") NewPassword: String?,
        @Field("ConfirmPassword") ConfirmPassword: String?
    ): Call<ResendOtpResponse?>?


}