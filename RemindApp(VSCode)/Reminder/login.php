<?php
// DEBUGGING:
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

require 'connect.php';

session_start();

if(isset($_POST['email']) && isset($_POST['password'])){
    $unsanitized_username = $_POST['username'];
    $unsanitized_pwd = $_POST['pwd'];

    // sanitize the data
    $username = filter_var($unsanitized_username, FILTER_SANITIZE_STRING);
    $pwd = filter_var($unsanitized_pwd, FILTER_SANITIZE_STRING);
    
    // validate that username exists in database
    // - use prepare statements to prevent sql injections
    $query = "SELECT pwd FROM `user` WHERE `username` = :username";
    $stmt = $db->prepare($query);
    $stmt->bindParam(':username', $username);

    // get results
    $result = $stmt->execute();
    $row = $result->fetchArray(SQLITE3_ASSOC);
    if($row)
    {
        // now check that the hashed password is correct
        if (password_verify($pwd, $row['pwd']))
        {
            $_SESSION['loggedin'] = true;
            $_SESSION['username'] = $username;
        }
    }
    else
    {
        $_SESSION['loggedin'] = false;
    }
}
?>
