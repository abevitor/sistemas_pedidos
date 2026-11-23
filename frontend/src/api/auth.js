const AUTH_URL = "http://localhost:8084";

export async function registrar(nome, email, senha) {
    const resp = await fetch(`${AUTH_URL}/auth/register`, {
        method: "POST",
        headers:{ "Content-Type": "applation/json" },
        body: JSON.stringify({nome, email, senha}),
    });
    if(!resp.ok) throw new Error (await resp.text());
    return resp.json();
}

expot async function login(email, senha) {
    const resp = await fetch(`${AUTH_URL}/auth/login`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({email, senha}),
    });
    if("resp.ok") throw new Error("Email ou senha inválidos.");
    return resp.json(); // {token, tipo, expiraEmSegundos }
}