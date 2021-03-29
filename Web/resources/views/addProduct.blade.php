@extends('layout')


@section('content')

    <div class="page-container">

    <div class="col-lg-9">
        <div class="card">
            <div class="card-header">
                <strong>Aktüel Ekle({{$data['store']->storeName}})</strong>
            </div>
            <div class="card-body card-block">
                <form action="{{route('product.store')}}" method="post" enctype="multipart/form-data" class="form-horizontal">
                    @csrf
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="text-input" class=" form-control-label">Aktüel geçerlilik tarihi</label>

                        </div>
                        <div class="col-12 col-md-9">
                            <input type="text" id="text-input" required name="date" placeholder="" class="form-control">
                            <small class="help-block form-text">Örneğin : 05-07 Ekim</small>
                            <input type="hidden" value="{{$data['store']->id}}" name="storeId">
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="file-input" class=" form-control-label">Aktüel</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <input type="file" id="file-input" required name="patch" class="form-control-file">
                        </div>
                    </div>

                    <div class="row form-group">
                        <div class="col col-md-3">
                            <label for="file-input" class=" form-control-label">Aktüel Resmi</label>
                        </div>
                        <div class="col-12 col-md-9">
                            <input type="file" id="file-input" required name="image" class="form-control-file">
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



    <div class="page-container">
        <!-- HEADER DESKTOP-->
        <!-- MAIN CONTENT-->
        <div class="main-content">
            <div class="section__content section__content--p30">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-15">
                            <h2 class="title-1 m-b-25">{{$data['store']->storeName}}</h2>
                            <div class="table-responsive table--no-card m-b-40">
                                <table class="table table-borderless table-striped table-earning">
                                    <thead>
                                    <tr>
                                        <th>Aktüel tarihi</th>
                                        <th>Aktüel resmi</th>
                                        <th>Atktüel içeriği</th>
                                        <th>Silme</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    @foreach($data['product'] as $product)
                                        <tr id="item-{{$product->id}}">
                                            <td>{{$product->date}}</td>
                                            <td><img src="/public/images/products/{{$product->image}}" height="100" width="100"  alt=""></td>
                                            <td>{{$product->patch}}</td>
                                            <td>

                                                <div class="d-flex justify-content-center">


                                                    <a href="javascript:void (0)"> <button type="submit" name="okulsilme"
                                                                                           class="btn btn-danger btn-sm btn-icon-split">
                        <span class="icon text-white-60">
                          <i id="@php echo $product->id; @endphp" class="fas fa-trash"></i>
                        </span>
                                                        </button></a>

                                                </div>


                                            </td>
                                        </tr>
                                    @endforeach
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- END MAIN CONTENT-->
        <!-- END PAGE CONTAINER-->
    </div>
    <script type="text/javascript">
        $(function () {

            $.ajaxSetup({
                headers: {
                    'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
                }
            });

        });

        $(".fa-trash").click(function () {
            destroy_id = $(this).attr('id');
            alertify.confirm('Silme işlemini onaylayın!', 'Bu işlem geri alınamaz',
                function () {
                    $.ajax({
                        type: "DELETE",
                        url: "product/"+destroy_id,
                        success: function (msg) {
                            if (msg) {
                                $("#item-" + destroy_id).remove();
                                alertify.success('Silme işlemi Başarılı...')

                            } else {
                                alertify.error('Silme işlemi Başarısız..')

                            }

                        }
                    });
                },
                function () {
                    alertify.error('Silme işlemi iptal edildi')
                }
            )

        });
    </script>

@endsection
