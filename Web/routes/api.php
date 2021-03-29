<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('/getStore', 'StoreController@getStore');
Route::post('/searchStores', 'StoreController@searchStores');
Route::get('/getProduct/{id}', 'ProductController@getProduct');
Route::get('/allProduct', 'ProductController@allProduct');
Route::get('/add', 'ProductController@add');
Route::post('/getSearch', 'SearchController@store');
Route::post('/addOrder', 'OrderController@add');
Route::get('/getOrder', 'OrderController@get');
Route::get('/ios', 'OrderController@ios');
