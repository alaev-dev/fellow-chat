import vweb
import os

struct App {
	vweb.Context
}

const webhook_password = 'your_password_here' // Установите ваш пароль здесь

@['/webhook'; post]
fn (mut app App) webhook() vweb.Result {
	// Получаем заголовок Authorization
	auth_header := app.req.header.get_custom('Authorization') or { '' }

	// Проверяем заголовок
	if auth_header != 'Bearer ${webhook_password}' {
		app.set_status(401, '')
		return app.text('Unauthorized')
	}

	// Выполняем скрипт
	os.system('./webhook-handler.sh')
	return app.ok('')
}

fn main() {
	mut app := App{}
	vweb.run(app, 30333)
}
