<!--
  $Id: nl-temperature.html,v 1.7 2006/05/25 10:47:04 justb Exp $
  author: Just van den Broecke
-->
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <meta http-equiv="Pragma" content="no-cache">
	<script type="text/javascript" src="js-pushlet-client.js"></script> 
     <script type="text/javascript">
       	// Initialization
		function init() {
			p_join_listen('/temperature');
  		}

		// Event Callback: display all events
		function onEvent(event) {
			document.eventDisplay.EVENT.value = event.toString();
		}
	</script>
</head>

<body bgcolor="#cccccc" onLoad="init()">
<script type="text/javascript">p_embed()</script>
<h4>Event Monitor</h4>

<form name="eventDisplay">
  <table border="2" bordercolor="white" cellpadding="4" cellspacing="0" >
    <tr>
     <td>
       <textarea cols="100" rows="20" name="EVENT">
        WAITING FOR EVENTS
       </textarea>
     </td>
    </tr>
  </table>
</form>
</body>
</html>