const form = document.getElementById("form");
const email = document.getElementById("email");
const password = document.getElementById("password");

form.addEventListener("submit", (e) => {
  e.preventDefault();
  logIn();
});

async function logIn() {
  const credencials = {
    email: email.value.trim(),
    password: password.value.trim(),
  };

  try {
    const response = await fetch("http://localhost:8092/auth/log-in", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(credencials),
    });
    if (response.status === 200) {
      const data = await response.json();
      alert(data.message);
      localStorage.setItem("username", data.username);
      localStorage.setItem("token", data.token);
      window.location.href = "./menu.html";
    }

    if (response.status === 400) {
      const data = await response.json();
      console.log(data);
      if (data.email != null) {
        alert(data.email);
      } else if (data.password != null) {
        alert(data.password);
      }
    }
    if (response.status === 401) {
      const dataErr = await response.text();
      alert(dataErr);
    }
    //console.log(data);
  } catch (error) {
    console.error(error);
  }
}
