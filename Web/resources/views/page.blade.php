@extends('layout')


@section('content')


    <div class="page-container">
        <!-- HEADER DESKTOP-->
        <!-- MAIN CONTENT-->
        <div class="main-content">
            <div class="section__content section__content--p30">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-15">
                            <h2 class="title-1 m-b-25">Marketler</h2>
                            <div class="table-responsive table--no-card m-b-40">
                                <table class="table table-borderless table-striped table-earning">
                                    <thead>
                                    <tr>
                                        <th>Market Adı</th>
                                        <th>Market Resmi</th>
                                        <th>Silme</th>
                                        <th>Mağaza İçeriği</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    @foreach($data as $store)
                                    <tr id="item-{{$store->id}}">
                                        <td>{{$store->storeName}}</td>
                                        <td><img src="public/images/stores/{{$store->picturePatch}}" height="100" width="100"  alt=""></td>
                                        <td>

                                            <div class="d-flex justify-content-center">


                                                <a href="{{route('store.edit',$store->id)}}">   <button type="submit" name="duzenleme"
                                                                                                      class="btn btn-success btn-sm btn-icon-split">
                        <span class="icon text-white-60">
                          <i class="fas fa-edit"></i>
                        </span>
                                                    </button></a>
                                                <a href="javascript:void (0)"> <button type="submit" name="okulsilme"
                                                                                       class="btn btn-danger btn-sm btn-icon-split">
                        <span class="icon text-white-60">
                          <i id="@php echo $store->id; @endphp" class="fas fa-trash"></i>
                        </span>
                                                    </button></a>

                                            </div>


                                        </td>
                                        <td>  <a  href="{{route('product.edit',$store->id)}}">  <button  type="submit" class="btn btn-success">Git</button></a>
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
                        url: "store/"+destroy_id,
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
