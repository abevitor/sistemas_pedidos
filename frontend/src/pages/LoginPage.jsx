import { useState } from "react";
import { login } from "../api/auth";
import { useAuth } from "../context/AuthContext";

export default function LoginPage({ aoIrParaCadastro }) {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const { entrar } = useAuth();

  async function enviar(e) {
    e.preventDefault();
    setErro("");
    try {
      const { token } = await login(email, senha);
      entrar(token); // guarda no contexto global; o App.jsx reage e troca de tela
    } catch (e) {
      setErro(e.message);
    }
  }

  return (
    <div className="max-w-sm mx-auto mt-10 space-y-8">
      <form onSubmit={enviar} className="space-y-3">
        <h2 className="text-xl font-semibold">Entrar</h2>
        <input
          className="w-full border rounded px-3 py-2"
          type="email"
          placeholder="e-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className="w-full border rounded px-3 py-2"
          type="password"
          placeholder="senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />
        {erro && <p className="text-red-600 text-sm">{erro}</p>}
        <button className="w-full bg-slate-800 text-white rounded py-2 hover:bg-slate-700">
          Entrar
        </button>
      </form>

      <p className="text-center text-sm">
        Não tem conta?{" "}
        <button onClick={aoIrParaCadastro} className="text-blue-600 underline">
          Cadastre-se
        </button>
      </p>
    </div>
  );
}