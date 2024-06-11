import AccountCreateComponent from '@/components/pageComponents/accountPage/accountCreateComponent'
import Screen from '@/components/screen'

export default function AccountAdd() {
  return (
    <main className='flex h-full min-h-screen w-full flex-col items-center justify-between p-3'>
      <Screen>
        <AccountCreateComponent />
      </Screen>
    </main>
  )
}
