# apod-backend
🔗 Backend da apod-frontend <a href="">apod-frontend</a> <br><br> usando o modelo de arquitetura MVC (Model, View, Controller)


## 📚 Endpoints 
  Os enpdoints que o frontend consome são não autenticados (/noauth), como os da tabale abaixo. Ao criar uma `subscription` é disparado um evento para fazer uma requisição na api da Nasa e enviar email para o usuário.

  - Todos os dias às 8:00 AM  (zone="America/Sao_Paulo) é disparado uma rotina para envio de emails <br><br>


| Method |  Route | Returns |
|----| ----| ---|
| GET | urlBase/noauth/subscriptions | subscription |
| POST | urlBase/noauth/subscriptions | subscription |


<br><br>
<center>
  <img src="https://github.com/Lebackrobot/apod-backend/assets/49316490/37463ded-95aa-4b26-9c70-84d056147b97" width="800">
</center>
