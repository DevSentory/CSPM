import AccountEditComponent from '@/components/pageComponents/accountPage/accountEditComponents'
import Screen from '@/components/screen'

export default function AccountEditPage() {
  return (
    <main className='flex h-full min-h-screen w-full flex-col items-center justify-between p-3'>
      <Screen>
        <AccountEditComponent />
      </Screen>
    </main>
  )
}
