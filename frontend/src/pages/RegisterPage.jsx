import { useState } from "react";
import { registrar } from "../api/auth";

export default function RegisterPage({ aoCadastrar }) {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");

  async function enviar(e) {
    e.preventDefault();
    setErro("");
    try {
      await registrar(nome, email, senha);
      alert("Cadastrado! Agora faça login.");
      aoCadastrar();
    } catch (e) {
      setErro("Erro ao cadastrar: " + e.message);
    }
  }

  return (
    <form onSubmit={enviar} className="space-y-3 max-w-sm mx-auto">
      <h2 className="text-xl font-semibold">Cadastrar</h2>
      <input
        className="w-full border rounded px-3 py-2"
        placeholder="nome"
        value={nome}
        onChange={(e) => setNome(e.target.value)}
      />
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
        placeholder="senha (mín. 8 caracteres)"
        value={senha}
        onChange={(e) => setSenha(e.target.value)}
      />
      {erro && <p className="text-red-600 text-sm">{erro}</p>}
      <button className="w-full bg-slate-800 text-white rounded py-2 hover:bg-slate-700">
        Cadastrar
      </button>
    </form>
  );
}