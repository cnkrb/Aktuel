<?php

namespace App\Http\Controllers;

use App\Order;
use Illuminate\Http\Request;

class OrderController extends Controller
{
    //  'address',
    //        'order',
    //        'value'
    public function add(Request $request){

        $store=Order::create([
            'address' => $request->address,
            'order' => $request->order,
            'value' => $request->value,
        ]);
        if($store){
            return response()->json([
                'status_code' => 'Sipariş Alındı'
            ]);
        }else {
            return response()->json([
                'status_code' => 'Sipariş Başarısız'
            ]);
        }

    }
    public function get(){
        $store=Order::all();
        return response()->json($store);
    }

    public function ios(){
        return response()->json([
            'status_code' => 'Deneme Başarılı'
        ]);
    }
}
