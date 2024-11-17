<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Failed</title>
    <style>
        body {
font-family: Arial, sans-serif;
background-color: #f9f9f9;
color: #333;
margin: 0;
padding: 0;
}
.container {
width: 80%;
max-width: 600px;
margin: 50px auto;
padding: 20px;
background-color: #fff;
border-radius: 8px;
box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.error-message {
color: #e74c3c;
font-size: 18px;
font-weight: bold;
}
.order-id {
margin-top: 20px;
font-size: 16px;
font-weight: bold;
color: #555;
}
.retry-button {
display: inline-block;
padding: 10px 20px;
background-color: #3498db;
color: white;
text-decoration: none;
border-radius: 5px;
margin-top: 20px;
}
.retry-button:hover {
background-color: #2980b9;
}
</style>
</head>
<body>
<div class="container">
        <h1>Order Failed</h1>

        <div class="error-message">
            We encountered an issue processing your order. Please try again later.
        </div>

        <div class="order-id">
            <p><strong>Order ID:</strong> ${order.id}</p>
        </div>

        <a href="" class="retry-button">Try Again</a>
    </div>
</body>
</html>
