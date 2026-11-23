import { useEffect } from "react";
import { ORDERS_STREAM_URL } from "../api/orders";

// onPedidoAtualizado é uma função que o componente que usa este hook passa,
// chamada toda vez que um evento "pedido-atualizado" chega
export function useOrderStream(onPedidoAtualizado) {
  useEffect(() => {
    const stream = new EventSource(ORDERS_STREAM_URL);

    stream.addEventListener("pedido-atualizado", (evento) => {
      const pedido = JSON.parse(evento.data);
      onPedidoAtualizado(pedido);
    });

    stream.onerror = () => {
      console.log("Conexão SSE caiu, o navegador tenta reconectar sozinho...");
    };

    // função de limpeza: roda quando o componente sai da tela
    return () => stream.close();
  }, [onPedidoAtualizado]);
}