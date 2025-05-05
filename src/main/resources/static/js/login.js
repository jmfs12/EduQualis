const loginForm = document.getElementById('login-form');
const registerForm = document.getElementById('register-form');
const formTitle = document.getElementById('form-title');
const switchText = document.querySelector('.switch');

function toggleForms(){
    const isLogin = loginForm.style.display !== 'none';
    loginForm.style.display = isLogin ? 'none' : 'block';
    registerForm.style.display = isLogin ? 'block' : 'none';
    formTitle.innerText = isLogin ? 'Registrar' : 'Login';
    switchText.innerText = isLogin ? 'Faça Login aqui' : 'Registre-se aqui';
}

document.getElementById('register-form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const username = document.getElementById("reg-name").value.trim();
    const email = document.getElementById("reg-email").value.trim();
    const password = document.getElementById("reg-password").value.trim();

    if (username.length < 3) {
        alert("Name must be at least 3 characters long");
        return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        alert("Please enter a valid email address");
        return;
    }

    if (password.length < 6) {
        alert("Password must be at least 6 characters long");
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/v1/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username, email, password})
        });

        if (response.ok) {
            alert('Registration successful!');
        } else {
            alert('Error during registration');
        }
    } catch (error) {
        console.error(error);
        alert('Could not connect to server');
    }
});

document.getElementById('login-form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const username = document.getElementById("log-name").value.trim();
    const password = document.getElementById("log-password").value.trim();

    try {
        const response = await fetch(`http://localhost:8080/api/v1/users/list?username=${encodeURIComponent(username)}`);
        if(!response.ok) {
            alert('Invalid username or password');
            return;
        }

        const data = await response.json();

        console.log(data.password)

        const checkPass = data.password === password;

        if (response.ok) {
            if (checkPass){
                alert('Login successful!');
            }
            else{
                alert('Invalid username or password');
            }
        } else {
            alert('Invalid username or password');
        }
    } catch (error) {
        console.error(error);
        alert('Could not connect to server');
    }
});