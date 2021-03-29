<?php

namespace App\Http\Controllers;

use App\Product;
use App\Store;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class ProductController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $data=Product::all();
        return  view('');
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {

        if($request->file('patch'))
        {
            $file = $request->file('patch');
            $filename = time() . '.' . $request->file('patch')->extension();
            $filePath = public_path('public/images/products');
            $file->move($filePath, $filename);

            $int = (int)+$request->storeId;

            /*$store=Product::create([
                'storeId' => $int,
                'image' => 'asdad',
                'patch' => $filename,
                'date' => $request->date,

            ]);*/

            if ($request->hasFile('image')) {


                /*  $request->validate([
                      'image' => 'image|mimes:jpg,png|max:2048'
                  ]);*/
                $filename2 = uniqid() . '.' . $request->image->getClientOriginalExtension();
                $request->image->move(public_path('public/images/products'), $filename2);

                $int = (int)+$request->storeId;

                $result =  DB::table('stores')
                    ->where('id',$request->storeId)
                    ->first();

                $store=Product::create([
                    'storeId' => $int,
                    'patch' => $filename,
                    'image' => $filename2,
                    'store' => $result->storeName,
                    'date' => $request->date,

                ]);

                if($store){
                    return redirect(route('product.edit',$int))->with('success', 'İşlem Başarılı');
                }
            }
        }


        else
        { echo $request->storeId;}
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
        $data['store']=Store::where('id',$id)->first();
        $data['product']=Product::where('storeId',$id)->get();

        return  view('addProduct',compact('data',$data));
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
        //
    }


    public function destroy($id)
    {

        $store = Product::find(intval($id));
        echo $store->patch;
        if ($store->delete()) {
            $path = '/public/images/products/' . $store->patch;
            if (file_exists($path)) {
                @unlink(public_path($path));
            }
            echo 1;
        }
        echo 0;

    }

    public function  getProduct($id){
        $data=Product::where('storeId',$id)->get();
        return response()->json($data);
    }


    public function  allProduct(){
        $data=Product::all();
        return response()->json($data);
    }


    public function add(){
        $data = Product::orderBy('created_at', 'desc')->take(10)->get();
        return response()->json($data);
    }
}
