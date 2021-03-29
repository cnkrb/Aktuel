<?php

namespace App\Http\Controllers;

use App\Store;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class StoreController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $data=Store::all();
        return view('page',compact('data',$data));

    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return  view('addStore');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        if ($request->hasFile('store_picture')) {
            $request->validate([
                'picturePatch' => 'image|mimes:jpg,png|max:2048'
            ]);
            $filename = uniqid() . '.' . $request->store_picture->getClientOriginalExtension();
            $request->store_picture->move(public_path('public/images/stores'), $filename);


           $store=Store::insert([
               'storeName' => $request->store_name,
               'picturePatch' => $filename,

           ]);

           if($store){

               return redirect(route('store.index'))->with('success', 'İşlem Başarılı');
           }
        }

    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $data=Store::find($id);
        return  view('editStore',compact('data',$data));

    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        if ($request->hasFile('store_picture')) {
            $request->validate([
                'picturePatch' => 'image|mimes:jpg,png|max:2048'
            ]);
            $filename = uniqid() . '.' . $request->store_picture->getClientOriginalExtension();
            $request->store_picture->move(public_path('public/images/stores'), $filename);


            $store=Store::where('id',$id)->update([
                'storeName' => $request->store_name,
                'picturePatch' => $filename,

            ]);

            if($store){
                $path = 'public/images/stores/' . $request->oldFile;
                if (file_exists($path)) {

                    @unlink(public_path($path));

                }

                return redirect(route('store.index'))->with('success', 'İşlem Başarılı');
            }
            else
            {
                return back()->with('error','Güncelleme Başarısız..');

            }
        }else {
            $store=Store::where('id',$id)->update([
                'storeName' => $request->store_name,

            ]);

            if($store){

                return redirect(route('store.index'))->with('success', 'İşlem Başarılı');
            }
            else{
                return back()->with('error','Güncelleme Başarısız..');

            }
        }

    }

    public function generateRandomString($length = 20)
    {
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $charactersLength = strlen($characters);
        $randomString = '';
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        return $randomString;
    }

    public function destroy($id)
    {
        $store = Store::find(intval($id));
        if ($store->delete()) {
            $path = '/public/images/stores/' . $store->picturePatch;
            if (file_exists($path)) {

                @unlink(public_path($path));

            }
            echo 1;
        }
        echo 0;
    }

    public function getStore(){
        $data=Store::all();
        return response()->json($data);

    }

    public  function  searchStores(Request $request){
        $result =  DB::table('stores')
            ->where('storeName', 'LIKE', '%'.$request->name.'%')
            ->get();
        return response()->json($result);
    }
}
