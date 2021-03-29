@extends('layout')


@section('content')

    <div class="page-container">

    <div class="col-lg-9">
        <div class="card">
            <div class="card-header">
                <strong>Mağaza Ekle</strong>
            </div>
            <div class="card-body card-block">
                <form action="{{route('store.store')}}" method="post" enctype="multipart/form-data" class="form-horizontal">
                    @csrf
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="text-input" class=" form-control-label">Mağaza Adı</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <input type="text" id="text-input" required name="store_name" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="file-input" class=" form-control-label">Mağaza Resmi</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <input type="file" id="file-input" required name="store_picture" class="form-control-file">
                        </div>
                    </div>
                    <div class="form-group" align="right">
                        <button type="submit" class="btn btn-success">Ekle</button>
                    </div>

                </form>
            </div>

        </div>
    </div>

    </div>
@endsection
