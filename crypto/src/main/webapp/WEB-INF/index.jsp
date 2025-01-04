
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="/home/img/apple-icon.png">
    <link rel="icon" type="image/png" href="/home/img/favicon.png">
    <title>
        T e a m  F l o w
    </title>
    <style>

/* Permet au body et à la section principale de prendre toute la hauteur */
html, body {
    height: 100%;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
}

/* La section principale prend tout l'espace disponible */
section {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

/* Centrer le footer */
footer {
    text-align: center;
    width: 100%;
    background-color: #f8f9fa; /* Ajustez la couleur selon votre thème */
    padding: 10px 0;
}


/* Scroll personnalisé si nécessaire (en option) */
::-webkit-scrollbar {
    width: 8px; /* Largeur de la scrollbar */
}
::-webkit-scrollbar-thumb {
    background-color: #888; /* Couleur de la barre */
    border-radius: 4px;
}
::-webkit-scrollbar-thumb:hover {
    background-color: #555; /* Couleur au survol */
}

    </style>
    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet" />
    <!-- Nucleo Icons -->
    <link href="/home/css/nucleo-icons.css" rel="stylesheet" />
    <link href="/home/css/nucleo-svg.css" rel="stylesheet" />
    <!-- Font Awesome Icons -->
    <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
    <link href="/home/css/nucleo-svg.css" rel="stylesheet" />
    <!-- CSS Files -->
    <link id="pagestyle" href="/home/css/soft-ui-dashboard.css?v=1.0.3" rel="stylesheet" />
</head>

<body class="g-sidenav-show  bg-gray-100">
    <!-- Navbar -->
    <nav
        class="navbar navbar-expand-lg position-absolute top-0 z-index-3 w-100 shadow-none my-3 navbar-transparent
        mt-4">
        <div class="container">
            <a class="navbar-brand font-weight-bolder ms-lg-0 ms-3 text-white" href="../pages/dashboards/default.html">
                C r y p t o m o n e y
            </a>
        </div>

    </nav>
    <!-- End Navbar -->
    <section >
        <div class="page-header align-items-start min-vh-50 pt-5 pb-11"
            style="background-image: url('..//home/img/curved-images/curved14.jpg');">
            <span class="mask bg-gradient-dark opacity-6"></span>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 text-center mx-auto">
                        <h1 class="text-white mb-2 mt-5">Bienvenue !</h1>
                        <p class="text-lead text-white">Ici vous pouvez faire de la cryptomonaie en toute sécurité  .</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row mt-lg-n10 mt-md-n11 mt-n10 ">
                <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
                    <div class="card z-index-0">
                        <div class="card-body">
                            <form action="login" method="POST">
                                <div class="mb-3 mt-2">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" placeholder="Entrez votre email" 
                                           aria-label="email" aria-describedby="email" name="email" id="email">
                                </div>

                                <div class="mb-1">
                                    <label for="password" class="form-label">Mot de passe</label>
                                    <input type="password" class="form-control" placeholder="Entrez votre mot de passe" 
                                           aria-label="password" aria-describedby="password" name="password" id="password">
                                </div>

                                <div class="text-center">
                                    <button type="submit" class="btn bg-gradient-dark w-100 my-4 mb-2">Se connecter</button>
                                </div>
                            </form>

                            <div class="text-center">
                                <p>Vous n'avez pas encore de compte ? 
                                    <a href="register" class="text-primary">Cliquez ici pour vous inscrire</a>
                                </p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- -------- START FOOTER 3 w/ COMPANY DESCRIPTION WITH LINKS & SOCIAL ICONS & COPYRIGHT ------- -->
    <footer class="py-5 mt-2">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 offset-2 mb-4 mx-auto text-center">
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-5 me-3 mb-sm-0 mb-2 offset-1">
                        @ Cryptomonaie Production
                    </a>
                </div>
                <div class="col-lg-8 mx-auto text-center mb-4 mt-2">
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class="text-lg fab fa-dribbble"></span>
                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class="text-lg fab fa-twitter"></span>
                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class="text-lg fab fa-instagram"></span>
                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class="text-lg fab fa-pinterest"></span>
                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class="text-lg fab fa-github"></span>
                    </a>
                </div>
            </div>
        </div>
    </footer>
    <!-- -------- END FOOTER 3 w/ COMPANY DESCRIPTION WITH LINKS & SOCIAL ICONS & COPYRIGHT ------- -->
    <!--   Core JS Files   -->
    <script src="/home/js/core/popper.min.js"></script>
    <script src="/home/js/core/bootstrap.min.js"></script>
    <script src="/home/js/plugins/perfect-scrollbar.min.js"></script>
    <script src="/home/js/plugins/smooth-scrollbar.min.js"></script>
    <script>
        var win = navigator.platform.indexOf('Win') > -1;
        if (win && document.querySelector('#sidenav-scrollbar')) {
            var options = {
                damping: '0.5'
            }
            Scrollbar.init(document.querySelector('#sidenav-scrollbar'), options);
        }
    </script>
    <!-- Github buttons -->
    <script async defer src="https://buttons.github.io/buttons.js"></script>
    <!-- Control Center for Soft Dashboard: parallax effects, scripts for the example pages etc -->
    <script src="/home/js/soft-ui-dashboard.min.js?v=1.0.3"></script>
</body>

</html>