import MultiFilterMenuBar from '@/components/pageComponents/resourcePage/resourceScanMultiFilterMenuBar'
import Screen from '@/components/screen'
import ResourceTable from '@/components/pageComponents/resourcePage/resourceTable'
export default function Home() {
  return (
    <main className='flex h-full w-full flex-col items-center justify-between p-3'>
      <Screen>
        <MultiFilterMenuBar />
        <ResourceTable />
      </Screen>
    </main>
  )
}
