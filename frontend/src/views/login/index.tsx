import { useMutation } from "@tanstack/react-query"
import { loginPost } from "../../api"
import { useForm } from '@mantine/form';
import { Button, PasswordInput, TextInput } from "@mantine/core";

const Login = () => {
  const form = useForm({
    mode: "uncontrolled",
    initialValues: {
      email: "",
      password: "",
    },
    validate: {
      email: (value) => (/^\S+@\S+$/.test(value) ? null : 'Invalid email'),
      password: (value: string) => (value.length < 6 ? 'Password too short' : null),
    }
  })
  const { mutateAsync: loginMutation, isPending } = useMutation({
    mutationFn: async (values) => {
      const response = await loginPost("/auth/login", values)
      return response;
    },
    onSuccess: () => {
      console.log("Logged in")
    },
    onError: (error) => {
      console.error("Login failed: ", error);
    }
  })
  return (
    <div>Login
      <form onSubmit={form.onSubmit((values) => loginMutation(values))}>
        <TextInput
          withAsterisk
          label="Email"
          placeholder="Your email"
          {...form.getInputProps("email")}
        />
        <PasswordInput
          withAsterisk
          label="Password"
          placeholder="Password"
          mt="md"
          {...form.getInputProps("password")}
        />
        <Button type="submit" mt="xl" loading={isPending}> Login</Button>
      </form>
    </div>
  )
}

export default Login