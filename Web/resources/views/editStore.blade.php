@extends('layout')


@section('content')

    <div class="page-container">

    <div class="col-lg-9">
        <div class="card">
            <div class="card-header">
                <strong>Mağaza Düzenle</strong>
            </div>
            <div class="card-body card-block">
                <form action="{{route('store.update',$data->id)}}" method="post" enctype="multipart/form-data" class="form-horizontal">
                    @csrf
                    @method('put')
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="text-input" class=" form-control-label">Mağaza Adı</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <input type="text" id="text-input" value="{{$data->storeName}}" required name="store_name" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="file-input" class=" form-control-label">Mağaza Resmi</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <input type="file" id="file-input"  name="store_picture" class="form-control-file">
                            <input type="hidden" value="{{$data->picturePatch}}" name="oldFile">
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="file-input" class=" form-control-label">Mağazanın Mevcut Resmi</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <td><img src="/public/images/stores/{{$data->picturePatch}}" height="100" width="100"  alt=""></td>
                        </div>
                    </div>
                    <div class="form-group" align="right">
                        <button type="submit" class="btn btn-success">Düzenle</button>
                    </div>

                </form>
            </div>

        </div>
    </div>

    </div>
@endsection
