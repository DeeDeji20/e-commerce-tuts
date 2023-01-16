<!DOCTYPE html
    PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Three PhaseFirst Vend Invoice</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@200;400;500;600;700;800;900&display=swap"
        rel="stylesheet">
</head>

<body style="margin: 0; padding: 0; font-family:  'Raleway', sans-serif; font-size: 13px; background-color: lightgrey;">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">


        <tr>
            <td style="padding: 0px 0 30px 0;">
                <!-- MAIN TABLE CONTENT -->
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="800"
                    style=" border-collapse: collapse; border: 1px solid lightgrey; background-color: #fff;">

                    <tr>
                        <td bgcolor="#ffffff" style="padding:0 1.5rem ;">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <table style="width: 100%;">

                                    <tr>
                                        <td>
                                            <div style="height: 75px;">
                                                <div style="margin-top: 20px;display: inline;/* height: 100px; */">
                                                    <img src="https://res.cloudinary.com/dt0wfaxft/image/upload/v1649243047/BEDC-Logo_bkd7a2.png"
                                                        alt="bedc_logo" width="150px" height="auto"
                                                        style="margin-top: 20px;">
                                                </div>
                                            </div>
                                            <p style="margin: 0; margin-bottom: 1rem; color: #6B6B6B; font-size: 13px;">
                                                No 5 Akpakpava Street, Benin, Nigeria</p>
                                        </td>

                                        <td>
                                            <div
                                                style="margin-left: 3rem; padding:30px 10px; background-color: #F2F2F2; width: 50%; transform: translateY(-15px)">
                                                <h1 style="color: #707070; text-align: center; margin: 0;">INVOICE</h1>
                                            </div>
                                        </td>
                                    </tr>
                                </table>

                                <table style="width: 100%;">
                                    <tr>
                                        <td>
                                            <h1 style="margin: 0; margin-bottom: 15px;">First Vend Invoice</h1>
                                        </td>
                                    </tr>
                                    <tr>


                                        <td>
                                            <table
                                                style="border: 1px solid #E4E4E4; border-collapse: collapse; width: 100%;">
                                                <tr>
                                                    <td style="border: 1px solid #E4E4E4; padding: 0 .5rem;"
                                                        colspan="1.5">
                                                        <p>Order ID</p>
                                                    </td>
                                                    <td style="border: 1px solid #E4E4E4; padding: 0 .5rem;">
                                                        <p><b>${orderId}</b></p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="border: 1px solid #E4E4E4; padding: 0 .5rem;">
                                                        <p>Date</p>
                                                    </td>
                                                    <td style="border: 1px solid #E4E4E4; padding: 0 .5rem;">
                                                        <p><b>${invoiceDate}</b></p>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                            <table style="margin-left: 3rem;">
                                                <tr>
                                                    <td>
                                                        <p>Customer Name</p>
                                                        <p><b>${customerName}</b></p>
                                                    </td>
                                                    <td style="padding-left: 1rem;">
                                                        <p>Email address</p>
                                                        <p><b>${email}</b></p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <p>Phone Number</p>
                                                        <p><b>${phoneNumber}</b></p>
                                                    </td>
                                                    <td style="padding-left: 1rem;">
                                                        <p>Address</p>
                                                        <p><b>${address}</b></p>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                    </tr>

                    <tr>
                        <td style="width: 100%;">

                            <table style="width: 100%;  border-collapse: collapse;">
                                <tr style="background-color: #FFE9E9; width: 100%;">
                                    <th colspan="1"
                                        style="color: #707070; padding: 1rem 0; border-top-left-radius: 10px;">Products
                                    </th>
                                    <th colspan="1" style="color: #707070; padding: 1rem 0;">Description</th>
                                    <th colspan="1"
                                        style="color: #707070; padding: 1rem 0;  border-top-right-radius: 10px;">Price
                                    </th>
                                </tr>

                                <tr style="background-color: #FBFBFE; width: 100%; border-bottom: 2px solid #495057;">
                                    <td colspan="1" style="width: 33%;">
                                        <p style="text-align: center; font-weight: 600; padding: 0 .5rem;">
                                            Token
                                        </p>
                                    </td>
                                    <td colspan="1" style="width: 33%;">
                                        <p style="text-align: center; font-weight: 600; padding: 0 .5rem;">{token}
                                        </p>
                                    </td>
                                    <td colspan="1" style="width: 33%;">
                                        <p style="text-align: center; font-weight: 600; padding: 0 .5rem;">token</p>
                                    </td>
                                </tr>

                                <tr style="border-bottom: 1px solid #70707080">
                                    <td>

                                    </td>
                                    <td>

                                    </td>
                                    <td>
                                        <p style="text-align: center; padding: 1rem 0;"><span
                                                style="color: #ED1A23; font-weight: 700; font-size: 14px;"><b>Total</b></span>
                                            <span
                                                style="color: #707070; font-size: 25px; font-weight: bold;">&#8358;${total}</span>
                                        </p>
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="3">
                                        <p style="color: #495057; padding-top: 1.5rem;">Dear <b>${customerName}</b>,
                                            Your meter is ready for installation, Kindly vend
                                            <b>${vendCharge}</b> on this Meter Number <b>${meterNumber}</b> today.
                                        </p>
                                        <p
                                            style="color: #495057; padding-top: 1.5rem; border-bottom: 1px solid #70707080; padding-bottom: 1.8rem;">
                                            Thank you, BEDC</p>
                                    </td>
                                </tr>
                            </table>


                        </td>



                    <tr>
                        <td>
                            <p
                                style="text-align: center; padding: 0rem; color: #495057; font-size: 15px; text-decoration: none;">
                                Tel: +234 708 0671 191</p>
                            <p style="text-align: center; padding: .0rem; color: #495057; font-size: 15px;">Email:
                                customercomplaints@beninelectric.com</p>
                            <p
                                style="font-weight: bold; text-align: center; color: #6B6B6B; font-weight: 800; font-size: 15px; padding-top: 50px;">
                                Thanks once again for your patronage</p>
                            <p
                                style="color: #A42727; text-align: center; font-weight: 700; padding-top: 3rem; font-size: 15px; text-decoration: none;">
                                www.beninelectric.com</p>
                        </td>
                    </tr>
        </tr>

    </table>

    </table>
    </td>
    </tr>
    </table>
    </td>
    </tr>
    </table>
</body>

</html>