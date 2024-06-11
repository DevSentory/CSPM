'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'
import { useSelectType } from '@/components/Zustand/store'
import Link from 'next/link'
import { useState } from 'react'

interface Props {
  showModal: boolean
  setShowModal: (newValue: boolean) => void
}

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL
interface DataStatus {
  resultType: string
  message: string
}

export default function AccountUpdateModal({ showModal, setShowModal }: Props) {
  const handleToggleModal = () => {
    setShowModal(false)
  }

  const [dataStatus, setDataStatus] = useState<DataStatus | null>(null)

  const {
    inputClientValue,
    inputCode,
    inputAccountName,
    inputAccessKey,
    inputAccessSecretKey,
    inputComment,
    inputAccountId,
    beforeAccountName,
  } = useInputStore()
  const { regionSelected } = useSelectType()

  const list = {
    client: inputClientValue,
    code: inputCode,
    accountName: inputAccountName,
    accessKey: inputAccessKey,
    secretKey: inputAccessSecretKey,
    region: regionSelected,
    comment: inputComment,
    accountId: inputAccountId,
  }

  const UpdateFormData = async () => {
    try {
      const response = await fetch(`${BASE_URL}/accounts/${inputAccountId}/${beforeAccountName}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
        },
        credentials: 'include',
        body: JSON.stringify(list),
      })

      if (response.ok) {
        const data = await response.json()

        setDataStatus(data)
      } else {
        setDataStatus({
          resultType: 'Failed',
          message: 'error',
        })
      }
    } catch (err) {
      setDataStatus({
        resultType: 'Failed',
        message: 'Failed to fetch data',
      })
    }
  }

  return (
    <>
      {showModal && (
        <div className='fixed inset-0 z-50 flex  items-center justify-center'>
          <div className=' h-[250px] w-[400px] rounded-lg bg-gray-50 shadow-md'>
            <div className=' p-3 text-start'>
              <h5 className='text-lg font-bold text-black'>대상 수정</h5>
            </div>
            <hr />

            {dataStatus === null ? (
              <>
                <span className='flex h-[100px] w-full items-center justify-center text-black'>
                  대상을 수정하시겠습니까?
                </span>
                <div className='mt-4 flex items-center justify-center space-x-5'>
                  <div
                    className='flex items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-black text-white hover:bg-gray-300'
                    onClick={UpdateFormData}
                  >
                    수정
                  </div>
                  <button
                    className='flex items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                    onClick={handleToggleModal}
                  >
                    취소
                  </button>
                </div>
              </>
            ) : (
              <>
                <div className='flex h-[100px] w-full items-center justify-center text-black'>
                  {dataStatus.resultType} {dataStatus.message}
                </div>
                <div className='mt-4 flex items-center justify-center space-x-5'>
                  <Link href='/config/account'>
                    <div className='flex items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-black text-white hover:bg-gray-300'>
                      관리자 페이지로 가기
                    </div>
                  </Link>
                  <button
                    className='flex items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                    onClick={handleToggleModal}
                  >
                    취소
                  </button>
                </div>
              </>
            )}
          </div>
        </div>
      )}
    </>
  )
}
