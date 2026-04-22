
import { Card, Text, Title, Grid, Button, Stack, Group } from '@mantine/core';

const Dashboard = () => {
  return (
    <div>
      <Grid>
        <Grid.Col span={12}>
          <Title order={2} style={{textAlign: "center"}}>Banking Dashboard</Title>
        </Grid.Col>
        <Grid.Col span={6}>
          <Card shadow="sm" p="lg" radius="md" withBorder>
            <Stack>
              <Title order={4}>Account Balance</Title>
              <Text size="xl" w={700}>$12,345.67</Text>
              <Text size="sm" color="dimmed">Available Balance</Text>
            </Stack>
          </Card>
        </Grid.Col>
        <Grid.Col span={6}>
          <Card shadow="sm" p="lg" radius="md" withBorder>
            <Stack>
              <Title order={4}>Savings Account</Title>
              <Text size="xl" w={700}>$5,678.90</Text>
              <Text size="sm" color="dimmed">Interest Rate: 2.5%</Text>
            </Stack>
          </Card>
        </Grid.Col>
        <Grid.Col span={12}>
          <Card shadow="sm" p="lg" radius="md" withBorder>
            <Title order={4}>Recent Transactions</Title>
            <Stack mt="md">
              <Group p="apart">
                <Text>Deposit</Text>
                <Text color="green">+$500.00</Text>
              </Group>
              <Group p="apart">
                <Text>Withdrawal</Text>
                <Text color="red">-$200.00</Text>
              </Group>
              <Group p="apart">
                <Text>Transfer</Text>
                <Text color="blue">-$100.00</Text>
              </Group>
            </Stack>
          </Card>
        </Grid.Col>
        <Grid.Col span={12}>
          <Group p="center">
            <Button>Transfer Money</Button>
            <Button variant="outline">Pay Bills</Button>
            <Button variant="outline">View Statements</Button>
          </Group>
        </Grid.Col>
      </Grid>
    </div>
  )
}

export default Dashboard