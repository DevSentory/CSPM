'use client'
import React, { useEffect, useState } from 'react'
import ScanDetailModal from '@/components/modal/scanDetailModal'
import Pagination from '@/components/pagination'
import ExceptionResultModal from '@/components/modal/exceptionResultModal'
import { usePageination } from '@/components/Zustand/pageinationStore'
import { useScanResultException } from '@/components/Zustand/scanExceptionStore'
import FilterSection from '@/components/filterSection'
import { useCheckedItemsStore } from '@/components/Zustand/accountPageStore'
import ScanResultFetchData from '@/components/pageComponents/scanresultPage/table/scanResultFetchData'
import TableHeaders from '@/components/pageComponents/scanresultPage/table/scanResultTableHead'
import ScanResultTableRow from '@/components/pageComponents/scanresultPage/table/scanResultTableRow'

interface Props {
  scanTime: string
  rid: string
  vulnerabilityStatus: string
  accountName: string
  accountId: string
  resourceId: string
  client: string
  policySeverity: string
  policyTitle: string
  policyCompliance: string
}

export default function ScanResultTableComponent() {
  const [data, setData] = useState<Props[]>([])
  ScanResultFetchData(setData)

  const [showModal, setShowModal] = useState(false)
  const [resourceId, setResourceId] = useState<string>('')
  const [scanTime, setScanTime] = useState('')
  const [accountId, setAccountId] = useState('')
  const [accountName, setAccountName] = useState('')
  const [policyTitle, setPolicyTitle] = useState('')
  const [showResModal, setShowResModal] = useState(false)

  const { itemsPerPage, currentPage, setCurrentPage, setDataLength, setItemsPerPage } =
    usePageination()
  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentItems = data.slice(indexOfFirstItem, indexOfLastItem)

  useEffect(() => {
    setCurrentPage(1)
    setDataLength(data.length)
    setItemsPerPage(15)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [data])

  const { checkedItems, setCheckedItems, setSelectIndex } = useCheckedItemsStore()
  const isItemChecked = (index: number) => checkedItems.has(index)
  const toggleCheckbox = (index: number) => {
    const newCheckedItems = new Set<number>(checkedItems)
    if (isItemChecked(index)) {
      newCheckedItems.delete(index)
    } else {
      newCheckedItems.add(index)
    }
    setCheckedItems(newCheckedItems)
  }

  const toggleAllCheckboxes = () => {
    if (checkedItems.size === currentItems?.length) {
      setCheckedItems(new Set<number>())
    } else {
      const newCheckedItems = new Set<number>()
      for (let i = 0; i < currentItems!.length ?? 0; i++) {
        newCheckedItems.add(i)
      }
      setCheckedItems(newCheckedItems)
    }
  }

  useEffect(() => {
    const updatedCheckedItems = new Set(
      [checkedItems.values().next().value].filter((index) => index < (data?.length ?? 0)),
    )
    setCheckedItems(updatedCheckedItems)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [data.length])

  const {
    setAccountIdException,
    setReousrceIdException,
    setPolicyTitleException,
    setReset,
    setAccountNameException,
  } = useScanResultException()

  useEffect(() => {
    if (checkedItems.size === 0) {
      setReset()
    }

    if (checkedItems.size === 1) {
      const selectedIndex = checkedItems.values().next().value
      const selectedData = (data || [])[selectedIndex]

      if (selectedData) {
        setAccountIdException(selectedData.accountId)
        setAccountNameException(selectedData.accountName)
        setReousrceIdException(selectedData.resourceId)
        setPolicyTitleException(selectedData.policyTitle)
      } else {
        setReset()
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [checkedItems])

  const handleToggleModal = (
    index: number,
    resourceId: string,
    scanTime: string,
    accountId: string,
    accountName: string,
  ) => {
    setResourceId(resourceId)
    setScanTime(scanTime)
    setAccountId(accountId)
    setAccountName(accountName)
    setSelectIndex(index)
    setShowModal(!showModal)
  }

  const handleResModal = (
    index: number,
    resourceId: string,
    policyTitle: string,
    accountId: string,
    accountName: string,
  ) => {
    setSelectIndex(index)
    setShowResModal(true)
    setPolicyTitle(policyTitle)
    setAccountId(accountId)
    setResourceId(resourceId)
    setAccountName(accountName)
  }

  useEffect(() => {
    setCheckedItems(new Set<number>())
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <div className='mt-1 h-[700px] w-full overflow-hidden'>
      <div className=''>
        <div className='flex h-[35px] w-full items-center gap-4 text-sm text-black'>
          <span>Total List: {data.length}</span>
          <div>
            {' '}
            <FilterSection />
          </div>
        </div>
        <TableHeaders
          toggleAllCheckboxes={toggleAllCheckboxes}
          allChecked={checkedItems.size === currentItems.length}
        />
        <div className='h-[560px] overflow-hidden'>
          {currentItems.map((item, index) => (
            <ScanResultTableRow
              key={index}
              item={item}
              index={index}
              isItemChecked={isItemChecked}
              toggleCheckbox={toggleCheckbox}
              handleToggleModal={handleToggleModal}
              handleResModal={handleResModal}
            />
          ))}
        </div>
        <Pagination />
      </div>
      {showModal && (
        <ScanDetailModal
          showModal={showModal}
          setShowModal={setShowModal}
          resourceId={resourceId}
          scanTime={scanTime}
          accountId={accountId}
          accountName={accountName}
        />
      )}
      {showResModal && (
        <ExceptionResultModal
          showResModal={showResModal}
          setShowResModal={setShowResModal}
          policyTitle={policyTitle}
          accountId={accountId}
          resourceId={resourceId}
          accountName={accountName}
        />
      )}
    </div>
  )
}
