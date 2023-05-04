<?php
// DEBUGGING:
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// connect to database
require 'connect.php';

session_start();

if(isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
    // store post values into variables
    $unsanitized_full_name = $_POST["full_name"];
    $unsanitized_pwd = $_POST["pwd"];
    $unsanitized_email = $_POST["email"];

    // sanitize the data
    $full_name = filter_var($unsanitized_full_name, FILTER_SANITIZE_STRING);
    $sanitized_pwd = filter_var($unsanitized_pwd, FILTER_SANITIZE_STRING);
    $email = filter_var($unsanitized_email, FILTER_SANITIZE_EMAIL);

    // hash the password before storing it into the database
    $pwd = password_hash($sanitized_pwd, PASSWORD_DEFAULT);

    // insert data into the database
    $insert = $db->prepare("INSERT INTO user(full_name, pwd, email) 
                            VALUES(:full_name, :pwd, :email)");

    // bind the values that the user gave
    $insert->bindValue(":full_name", $fname, SQLITE3_TEXT);
    $insert->bindValue(":pwd", $pwd, SQLITE3_TEXT);
    $insert->bindValue(":email", $email, SQLITE3_TEXT);

    // execute the statement
    $insert->execute();
}
?>
