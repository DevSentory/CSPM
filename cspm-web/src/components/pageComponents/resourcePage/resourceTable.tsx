'use client'
import { usePageination } from '@/components/Zustand/pageinationStore'
import FilterSection from '@/components/filterSection'
import ResourceScanModal from '@/components/modal/resourceScanModal'
import FetchData from '@/components/pageComponents/resourcePage/table/resourceFetchData'
import TableHead from '@/components/pageComponents/resourcePage/table/resourceTableHead'
import ResourceTableRow from '@/components/pageComponents/resourcePage/table/resourceTableRow'
import Pagination from '@/components/pagination'
import { useEffect, useState } from 'react'

interface Props {
  scanTime: string
  createTime: string
  client: string
  accountName: string
  accountId: string
  serviceGroup: string
  resourceId: string
  tag: string
}

export default function TableComponent() {
  const [data, setData] = useState<Props[]>([])

  FetchData(setData)

  const { currentPage, itemsPerPage, setDataLength, setItemsPerPage, setCurrentPage } =
    usePageination()

  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentItems = data?.slice(indexOfFirstItem, indexOfLastItem)

  const [showModal, setShowModal] = useState(false)
  const [resourceId, setResourceId] = useState<string>('')
  const [scanTime, setScanTime] = useState<string>('')
  const [accountId, setAccountId] = useState<string>('')
  const [accountName, setAccountName] = useState('')

  const handleToggleModal = (
    resourceId: string,
    accountId: string,
    scanTime: string,
    accountName: string,
  ) => {
    setShowModal(!showModal)
    setResourceId(resourceId)
    setScanTime(scanTime)
    setAccountId(accountId)
    setAccountName(accountName)
  }

  useEffect(() => {
    setCurrentPage(1)
    setDataLength(data.length)
    setItemsPerPage(14)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [data])

  return (
    <div className='mt-3 h-[700px] w-full overflow-hidden'>
      <div className='flex h-[35px] w-full items-center gap-4 text-sm text-black'>
        <span>Total List: {data.length}</span>
        <div>
          <FilterSection />
        </div>
      </div>
      <TableHead />
      <div className='h-[560px]'>
        {currentItems.length > 0 ? (
          currentItems.map((listIndex, index) => (
            <ResourceTableRow
              key={index}
              index={index}
              listIndex={listIndex}
              handleToggleModal={handleToggleModal}
            />
          ))
        ) : (
          <div className='mt-4 flex items-center justify-center'>리스트가 없습니다</div>
        )}
      </div>
      <Pagination />
      <ResourceScanModal
        showModal={showModal}
        setShowModal={setShowModal}
        resourceId={resourceId}
        scanTime={scanTime}
        accountId={accountId}
        accountName={accountName}
      />
    </div>
  )
}
