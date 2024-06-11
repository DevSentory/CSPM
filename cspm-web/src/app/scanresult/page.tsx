import ScanResultMultiFilterMenuBar from '@/components/pageComponents/scanresultPage/scanResultmultiFilterMenuBar'
import Screen from '@/components/screen'
import TableComponent from '@/components/pageComponents/scanresultPage/scanResultTableComponent'
export default function Home() {
  return (
    <main className='flex h-full w-full flex-col items-center justify-between p-3'>
      <Screen>
        <ScanResultMultiFilterMenuBar />
        <TableComponent />
      </Screen>
    </main>
  )
}
