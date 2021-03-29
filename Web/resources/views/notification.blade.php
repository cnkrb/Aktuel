@extends('layout')


@section('content')

    <div class="page-container">

    <div class="col-lg-9">
        <div class="card">
            <div class="card-header">
                <strong>notişfdi</strong>
            </div>
            <div class="card-body card-block">
                <form action="" method="post" enctype="multipart/form-data" class="form-horizontal">
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="text-input" class=" form-control-label">Mağaza Adı</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <input type="text" id="text-input" name="text-input" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="file-input" class=" form-control-label">Mağaza Resmi</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <input type="file" id="file-input" name="file-input" class="form-control-file">
                        </div>
                    </div>

                </form>
            </div>
            <div class="card-footer">
                <button type="submit" class="btn btn-primary btn-sm">
                    <i class="fa fa-dot-circle-o"></i> Ekle
                </button>
            </div>
        </div>
    </div>

    </div>
@endsection
