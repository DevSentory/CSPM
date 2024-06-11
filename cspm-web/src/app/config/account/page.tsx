import AccountMultiFilterMenuBar from '@/components/pageComponents/accountPage/accountMultiFilterMenu'
import AccoountTable from '@/components/pageComponents/accountPage/accountTable'
import Screen from '@/components/screen'

export default function ConfigPage() {
  return (
    <main className='flex h-full w-full flex-col items-center justify-between '>
      <Screen>
        <AccountMultiFilterMenuBar />
        <AccoountTable />
      </Screen>
    </main>
  )
}
