<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::resource('/store','StoreController');
Route::resource('/product','ProductController');
Route::get('/', 'DefaultController@homePage')->name('homePage');
Route::get('/info', 'DefaultController@info')->name('info');
Route::get('/notification', 'DefaultController@notification')->name('notification');
