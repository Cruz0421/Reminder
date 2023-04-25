<?php
if(isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
    require_once "connect.php";
    require_once "validate.php";
    $name = validatae($_POST['name']);
    $email = validate($_POST['email']);
    $password = validate($_POST['password']);
    $sql = "insert int users values('', '$name', '$email', '". md5($password) ."')";
    if(!conn->query($sql)) {
        echo "failure";
    } else {
        echo "success";
    }
}
?>