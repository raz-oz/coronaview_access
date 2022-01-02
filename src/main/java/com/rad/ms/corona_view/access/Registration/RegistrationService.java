package com.rad.ms.corona_view.access.Registration;


import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Registration.email.EmailSender;
import com.rad.ms.corona_view.access.Registration.token.ConfirmationToken;
import com.rad.ms.corona_view.access.Registration.token.ConfirmationTokenService;
import com.rad.ms.corona_view.access.Service.UserAccessService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Service
@EnableAsync
@AllArgsConstructor
public class RegistrationService implements IRegistrationService{
    private static final String ROLE_MONITOR = "3";

    @Autowired
    private UserAccessService userAccessService;

    private ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;

    @Autowired
    private final EmailSender emailSender;


    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        String token = userAccessService.addUser(request.getUsername(), request.getPassword(), ROLE_MONITOR);
        String link = "http://localhost:8403/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getUsername(), link));
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("User already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        User user = confirmationToken.getAppUser();
        user.setEnabled(true);
        userAccessService.updateUser(user.getUsername(), user);
        confirmationTokenService.setConfirmedAt(token);

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                <meta charset="utf-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>RAD inc</title>
                <link href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round" rel="stylesheet">
                <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
                <style>
                    body {
                		font-family: 'Varela Round', sans-serif;
                	}
                	.modal-confirm {		
                		color: #636363;
                		width: 325px;
                		margin: 30px auto;
                	}
                	.modal-confirm .modal-content {
                		padding: 20px;
                		border-radius: 5px;
                		border: none;
                	}
                	.modal-confirm .modal-header {
                		border-bottom: none;  \s
                        position: relative;
                	}
                	.modal-confirm h4 {
                		text-align: center;
                		font-size: 26px;
                		margin: 30px 0 -15px;
                	}
                	.modal-confirm .form-control, .modal-confirm .btn {
                		min-height: 40px;
                		border-radius: 3px;\s
                	}
                	.modal-confirm .close {
                        position: absolute;
                		top: -5px;
                		right: -5px;
                	}	
                	.modal-confirm .modal-footer {
                		border: none;
                		text-align: center;
                		border-radius: 5px;
                		font-size: 13px;
                	}	
                	.modal-confirm .icon-box {
                		color: #fff;		
                		position: absolute;
                		margin: 0 auto;
                		left: 0;
                		right: 0;
                		top: -70px;
                		width: 95px;
                		height: 95px;
                		border-radius: 50%;
                		z-index: 9;
                		background: #82ce34;
                		padding: 15px;
                		text-align: center;
                		box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
                	}
                	.modal-confirm .icon-box i {
                		font-size: 58px;
                		position: relative;
                		top: 3px;
                	}
                	.modal-confirm.modal-dialog {
                		margin-top: 80px;
                	}
                    .modal-confirm .btn {
                        color: #fff;
                        border-radius: 4px;
                		background: #82ce34;
                		text-decoration: none;
                		transition: all 0.4s;
                        line-height: normal;
                        border: none;
                    }
                	.modal-confirm .btn:hover, .modal-confirm .btn:focus {
                		background: #6fb32b;
                		outline: none;
                	}
                	.trigger-btn {
                		display: inline-block;
                		margin: 100px auto;
                	}
                </style>
                </head>
                <body>


                <!-- Modal HTML -->
                <div id="myModal" class="">
                	<div class="modal-dialog modal-confirm">
                		<div class="modal-content">
                			<div class="modal-header">
                				<div class="icon-box">
                					<i class="material-icons">&#xE876;</i>
                				</div>				
                				<h4 class="modal-title">Awesome!</h4>	
                			</div>
                			<div class="modal-body">
                				<p class="text-center">Excellent, your account has been activated Click here to go to the login page.</p>
                			</div>
                			<div class="modal-footer">
                				<button onClick="window.location.href='http://localhost:8403/'" class="btn btn-success btn-block">Go to login</button>
                			</div>
                		</div>
                	</div>
                </div>    \s
                </body>
                </html>                           \s
                """;
    }

    private String buildEmail(String name, String link) {
        return

"""    
    <tr>
        <td bgcolor="#f04b32" align="center" style="padding: 0px 10px 0px 10px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                <tr>
                    <td bgcolor="#ffffff" align="center" valign="top" style="padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;">
                        <h1 style="font-size: 48px; font-weight: 400; margin: 2;">Welcome!\n
                    
                    </h1> <img src=" https://img.icons8.com/clouds/100/000000/handshake.png" width="125" height="120" style="display: block; border: 0px;" />
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    
    <tr>
        <td bgcolor="#f4f4f4" align="center" style="padding: 0px 10px 0px 10px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                <tr>
                    <td bgcolor="#ffffff" align="left" style="padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;">
                        <p style="margin: 0;">Hi, """+name+
        """
                 we're so excited to have you get started. First, you need to confirm your account. Just press the button below.</p>
                </td>
                </tr>
                <tr>
                    <td bgcolor="#ffffff" align="left">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td bgcolor="#ffffff" align="center" style="padding: 20px 30px 60px 30px;">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td align="center" style="border-radius: 3px;" bgcolor="#f04b32"><a href="""+link+
                                        """
                                         target="_blank" style="font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #f04b32; display: inline-block;">Confirm Account</a></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr> <!-- COPY -->
                <tr>
                    <td bgcolor="#ffffff" align="left" style="padding: 0px 30px 0px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;">
                </td>
                </tr> <!-- COPY -->
                <tr>
                    <td bgcolor="#ffffff" align="left" style="padding: 20px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;">
                    </td>
                </tr>
                <tr>
                    <td bgcolor="#ffffff" align="left" style="padding: 0px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;">
                        <p style="margin: 0;">If you have any questions, just reply to this email—we're always happy to help out.</p>
                </td>
                </tr>
                <tr>
                    <td bgcolor="#ffffff" align="left" style="padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;">
                        <p style="margin: 0;">Cheers,<br>RAD Team</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td bgcolor="#f4f4f4" align="center" style="padding: 30px 10px 0px 10px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                <tr>
                    <td bgcolor="#FFECD1" align="center" style="padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;">
                        <h2 style="font-size: 20px; font-weight: 400; color: #111111; margin: 0;">Need more help?</h2>
                        <p style="margin: 0;"><a href="#" target="_blank" style="color: #f04b32;">We&rsquo;re here to help you out</a></p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td bgcolor="#f4f4f4" align="center" style="padding: 0px 10px 0px 10px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                <tr>
                    <td bgcolor="#f4f4f4" align="left" style="padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;"> <br>
                        <p style="margin: 0;">If these emails get annoying, please feel free to <a href="#" target="_blank" style="color: #111111; font-weight: 700;">unsubscribe</a>.</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

""";
    }

}