<!DOCTYPE html>
<html lang="tr">

<head>
    <!-- Required meta tags-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="au theme template">
    <meta name="author" content="Hau Nguyen">
    <meta name="keywords" content="au theme template">
    <meta name="csrf-token" content="{{ csrf_token() }}">

    <!-- Title Page-->
    <title>Aktüel Ürünler</title>

    <!-- Fontfaces CSS-->
   {{-- <link href="public/backend/css/font-face.css" rel="stylesheet" media="all">--}}
    <link href="/public/backend/css/font-face.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/font-awesome-5/css/fontawesome-all.min.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">

    <!-- Bootstrap CSS-->
    <link href="/public/backend/vendor/bootstrap-4.1/bootstrap.min.css" rel="stylesheet" media="all">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script src="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/alertify.min.js"></script>

    <!-- CSS -->
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css"/>
    <!-- Default theme -->
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/default.min.css"/>
    <!-- Semantic UI theme -->
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/semantic.min.css"/>
    <!-- Bootstrap theme -->
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/bootstrap.min.css"/>

    <!-- Vendor CSS-->
    <link href="/public/backend/vendor/animsition/animsition.min.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/wow/animate.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/css-hamburgers/hamburgers.min.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/slick/slick.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="/public/backend/vendor/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" media="all">

    <!-- Main CSS-->
    <link href="/public/backend/css/theme.css" rel="stylesheet" media="all">

</head>

<body class="animsition">
<div class="page-wrapper">
    <!-- HEADER MOBILE-->
    <header class="header-mobile d-block d-lg-none">
        <div class="header-mobile__bar">
            <div class="container-fluid">
                <div class="header-mobile-inner">
                    <a class="logo" href="index.html">
                        <img src="public/images/icon/logo.png" alt="Aktüel Ürünler" />
                    </a>
                    <button class="hamburger hamburger--slider" type="button">
                            <span class="hamburger-box">
                                <span class="hamburger-inner"></span>
                            </span>
                    </button>
                </div>
            </div>
        </div>
        <nav class="navbar-mobile">
            <div class="container-fluid">
                <ul class="navbar-mobile__list list-unstyled">

                    <li>
                        <a href="{{route('homePage')}}">
                            <i class="active fas fa-chart-bar"></i>Ana Sayfa</a>
                    </li>
                    <li>
                        <a href="{{route('store.index')}}">
                            <i class="fas fa-chart-bar"></i>Mağazalar</a>
                    </li>
                    <li>
                        <a href="{{route('store.create')}}">
                            <i class="fas fa-table"></i>Mağaz Ekle</a>
                    </li>
                    <li>
                        <a href="{{route('notification')}}">
                            <i class="far fa-check-square"></i>Bildirimleri Yönet</a>
                    </li>
                    <li>
                        <a href="{{route('info')}}">
                            <i class="fas fa-calendar-alt"></i>Hakkımda</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- END HEADER MOBILE-->

    <!-- MENU SIDEBAR-->
    <aside class="menu-sidebar d-none d-lg-block">
        <div class="logo">
            <a href="index.html">
                <img src="images/icon/logo.png" alt="Aktüel Ürünler" />
            </a>
        </div>
        <div class="menu-sidebar__content js-scrollbar1">
            <nav class="navbar-sidebar">
                <ul class="list-unstyled navbar__list">
                    <li>
                        <a href="{{route('homePage')}}">
                            <i class="active fas fa-chart-bar"></i>Ana Sayfa</a>
                    </li>
                    <li>
                        <a href="{{route('store.index')}}">
                            <i class="fas fa-chart-bar"></i>Mağazalar</a>
                    </li>
                    <li>
                        <a href="{{route('store.create')}}">
                            <i class="fas fa-table"></i>Mağaz Ekle</a>
                    </li>
                    <li>
                        <a href="{{route('notification')}}">
                            <i class="far fa-check-square"></i>Bildirimleri Yönet</a>
                    </li>
                    <li>
                        <a href="{{route('info')}}">
                            <i class="fas fa-calendar-alt"></i>Hakkımda</a>
                    </li>

                </ul>
            </nav>
        </div>
    </aside>
    <!-- END MENU SIDEBAR-->

    <!-- PAGE CONTAINER-->
    @yield('content')

</div>

<!-- Jquery JS-->
<script src="/public/backend/vendor/jquery-3.2.1.min.js"></script>
<!-- Bootstrap JS-->
<script src="/public/backend/vendor/bootstrap-4.1/popper.min.js"></script>
<script src="/public/backend/vendor/bootstrap-4.1/bootstrap.min.js"></script>
<!-- Vendor JS       -->
<script src="/public/backend/vendor/slick/slick.min.js">
</script>
<script src="/public/backend/vendor/wow/wow.min.js"></script>
<script src="/public/backend/vendor/animsition/animsition.min.js"></script>
<script src="/public/backend/vendor/bootstrap-progressbar/bootstrap-progressbar.min.js">
</script>
<script src="/public/backend/vendor/counter-up/jquery.waypoints.min.js"></script>
<script src="/public/backend/vendor/counter-up/jquery.counterup.min.js">
</script>
<script src="/public/backend/vendor/circle-progress/circle-progress.min.js"></script>
<script src="/public/backend/vendor/perfect-scrollbar/perfect-scrollbar.js"></script>
<script src="/public/backend/vendor/chartjs/Chart.bundle.min.js"></script>
<script src="/public/backend/vendor/select2/select2.min.js">
</script>




<!-- Main JS-->
<script src="/public/backend/js/main.js"></script>

</body>

</html>
