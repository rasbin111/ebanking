import '@mantine/core/styles.css';
import { MantineProvider } from "@mantine/core";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { createBrowserRouter } from 'react-router';
import { RouterProvider } from 'react-router/dom'
import Login from './views/login';
import Dashboard from './views/dashboard';

const queryClient = new QueryClient();
const router = createBrowserRouter([
  {
    path: "/",
    Component: Dashboard,
  },
  {
    path: "/login",
    Component: Login,
  }
])

function App() {

  return (
    <MantineProvider>
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
      </QueryClientProvider>
    </MantineProvider>
  )
}

export default App
