<?php
    $server = "ssh://artemis_csub/home/stu/pcruz/public_html/Reminder/";
    $username = "root";
    $password = "";
    $database = "register_from_android";
    $conn = new mysqli($server, $username, $password, $database);
    if($conn->connect_error){
        die("connection failed: ". $conn->connect_error);
    }
?>