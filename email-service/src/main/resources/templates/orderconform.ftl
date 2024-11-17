<#-- Order Confirmation Template with limited fields -->
<html>
<head>
<meta charset="UTF-8">
    <title>Order Confirmation</title>
    <style>
        body {
font-family: Arial, sans-serif;
margin: 0;
padding: 0;
background-color: #f9f9f9;
color: #333;
}
.container {
width: 100%;
max-width: 600px;
margin: 0 auto;
background-color: #fff;
padding: 20px;
border: 1px solid #ddd;
}
h1 {
color: #1a73e8;
font-size: 24px;
text-align: center;
}
.order-summary, .items-table {
margin-top: 20px;
}
.order-summary th, .order-summary td, .items-table th, .items-table td {
padding: 10px;
text-align: left;
}
.order-summary th, .items-table th {
background-color: #f1f1f1;
}
.items-table {
width: 100%;
border-collapse: collapse;
}
.items-table td {
border-top: 1px solid #ddd;
}
</style>
</head>
<body>
<div class="container">
        <h1>Order Confirmation</h1>
        <p>Hi,</p>
        <p>Your order has been successfully placed. Here are the details:</p>

        <div class="order-summary">
            <h3>Order Summary</h3>
            <table class="order-summary">
                <tr>
                    <th>Order ID</th>
                    <td>${orderId}</td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>${status}</td>
                </tr>
            </table>
        </div>

        <p>If you have any questions, feel free to reach out to us.</p>

        <div class="footer">
            <p>&copy; ${current_year} Your Company. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
